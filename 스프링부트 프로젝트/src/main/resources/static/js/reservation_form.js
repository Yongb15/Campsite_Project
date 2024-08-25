document.addEventListener('DOMContentLoaded', function() {
    const container = document.getElementById('checkbox-container');
    const rows = ['A', 'B', 'C', 'D', 'E'];
    const cols = [1, 2, 3, 4, 5];

    // 체크박스와 라벨을 동적으로 생성
    rows.forEach(row => {
        cols.forEach(col => {
            const value = `${row}${col}`;
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.value = value;
            checkbox.name = 'camping_idx';
            checkbox.id = value;
            checkbox.className = 'custom-checkbox';

            const label = document.createElement('label');
            label.htmlFor = value;
            label.className = 'checkbox-label';
            label.textContent = value;

            container.appendChild(checkbox);
            container.appendChild(label);
        });
    });

    // 가격 계산 함수
    function updatePrice() {
        const numPeople = parseInt(document.getElementById('numberInput').value, 10);
        const priceElement = document.getElementById('price');
        const priceHiddenElement = document.getElementById('priceHidden');
        let price = 0;
        if (numPeople > 0) {
            if (numPeople <= 2) {
                price = numPeople * 10000;
            } else {
                price = 2 * 10000 + (numPeople - 2) * 3000;
            }
        }
        priceElement.textContent = '금액: ' + price.toLocaleString() + '원';
        priceHiddenElement.value = price;
    }

    // 초기 금액 설정 및 인원 수 변경 시 금액 업데이트
    document.getElementById('numberInput').value = 1;
    updatePrice();
    document.getElementById('numberInput').addEventListener('input', updatePrice);

    // 날짜 선택기 초기화
    $("#datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
        minDate: 0,
        maxDate: "+1M +10D",
        changeMonth: true,
        changeYear: true,
        onSelect: function(selectedDate) {
            // 날짜가 선택되면 모든 체크박스를 초기화
            $("input[type='checkbox']").prop('checked', false);
			$("input[type='checkbox']").prop('disabled', false);
			$("input[type='checkbox']").next('label').text(function() {
			     return $(this).prev('input[type="checkbox"]').val();
			});
            // AJAX 요청을 통해 예약된 좌석 정보를 가져옴
            $.ajax({
                url: '/findReservedSeatsByDate', // 서버의 실제 URL 확인
                method: 'GET', // 요청 방법이 적절한지 확인
                data: { date: selectedDate }, // 날짜를 쿼리 파라미터로 전송
                dataType: 'json', // 예상하는 응답 형식 설정
                success: function(response) {
                    console.log('서버 응답:', response); // 응답 확인
                    // 서버 응답이 JSON 배열일 경우, 각 체크박스의 상태를 업데이트
                    const reservedSeats = Array.isArray(response) ? response : [];
                    $("input[type='checkbox']").each(function() {
                        if (reservedSeats.includes(this.value)) {
                            $(this).prop('disabled', true);
							$(this).next('label').text('예약 완료');
                        } else {
                            $(this).prop('disabled', false);
                        }
                    });
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('AJAX 요청 오류:', {
                        status: jqXHR.status,
                        textStatus: textStatus,
                        errorThrown: errorThrown
                    });
                    alert('좌석 정보를 가져오는 데 오류가 발생했습니다.');
                }
            });
        }
    });

    // 체크박스를 하나만 선택하도록 설정
    $(document).on('click', "input[type='checkbox']", function() {
        const checkboxes = $("input[type='checkbox']");
        if (this.checked) {
            checkboxes.not(this).prop('checked', false);
        }
    });

    // 예약하기 버튼 클릭 시 날짜 값을 확인
    $("#reserveButton").on("click", function() {
        const selectedDate = $("#datepicker").val();
        if (!selectedDate) {
            alert('날짜를 선택해 주세요!');
        }else {
            console.log('선택된 날짜:', selectedDate);
            $("form[name='reservation_insert']").submit();
        }
    });

    // 로그아웃
    let $logoutButton = $("div[name='logout']");
    $logoutButton.on("click", function() {
        location.href="/logout";
    });
});
document.addEventListener('DOMContentLoaded', function() {
    const menuLinks = document.querySelectorAll('.menu a');

    // 로그인 상태를 확인하는 함수
    function checkLoginStatus() {
        return new Promise((resolve, reject) => {
            fetch('/checkLoginStatus', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                resolve(data.loggedIn);
            })
            .catch(error => {
                console.error('로그인 상태 확인 중 오류 발생:', error);
                reject(error);
            });
        });
    }

    // 메뉴 링크 클릭 이벤트 설정
    function setupMenuLinks() {
        menuLinks.forEach(link => {
            link.addEventListener('click', function(event) {
                const href = this.getAttribute('href');
                event.preventDefault(); // 기본 링크 동작 방지

                checkLoginStatus().then(isLoggedIn => {
                    if (isLoggedIn) {
                        window.location.href = href; // 로그인되어 있으면 페이지 이동
                    } else {
                        alert("로그인 후 이용바랍니다.");
                        window.location.href = '/login'; // 로그인되지 않았으면 로그인 페이지로 이동
                    }
                }).catch(error => {
                    console.error('로그인 상태 확인 실패:', error);
                });
            });
        });
    }

    // jQuery의 ready() 함수
    $(document).ready(function() {
        const blockListSize = 10; // 페이지당 항목 수
        let currentPage = 1;
        let isLoading = false; // 로딩 중인지 확인

        function loadSubjects(page = 1) {
            if (isLoading) return; // 로딩 중이면 중복 요청 방지
            isLoading = true;

            $.ajax({
                url: '/getSubjects',
                method: 'GET',
                data: { page: page },
                success: function(data) {
                    const $eventsList = $('#events-list');

                    // 제목 리스트 로드
                    data.subjects.forEach(subject => {
                        $eventsList.append(`<li>${subject}</li>`);
                    });

                    currentPage = page;
                    isLoading = false; // 로딩 완료
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('AJAX request failed:', textStatus, errorThrown);
                    isLoading = false; // 로딩 오류 시에도 상태 변경
                }
            });
        }

        // 무한 스크롤 기능
        function setupInfiniteScroll() {
            $(window).on('scroll', function() {
                const scrollTop = $(window).scrollTop();
                const scrollHeight = $(document).height();
                const windowHeight = $(window).height();
                const scrollPosition = scrollTop + windowHeight;

                if (scrollPosition >= scrollHeight - 100) { // 100px 남았을 때
                    loadSubjects(currentPage + 1);
                }
            });
        }

        loadSubjects(); // 초기 데이터 로드
    });

    setupMenuLinks();
});