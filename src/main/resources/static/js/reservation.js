//오류 메시지 요소의 내용을 자바스크립트로 읽어오기
	var errorMessageElement = document.getElementById('error-message');
	if (errorMessageElement) {
		var errorMessage = errorMessageElement.textContent || errorMessageElement.innerText;			// 컨트롤러에서 에러 메세지 가져오기
		console.log('Error Message:', errorMessage); // 콘솔에서 확인
	}

	
	// 가격
	function updatePrice() {
	    var numPeople = parseInt(document.getElementById('numberInput').value, 10);						
	    var priceElement = document.getElementById('price');
	    var priceHiddenElement = document.getElementById('priceHidden');								// hidden에다가 값을 보냄
	    var price = 0;
	    if (numPeople > 0) {
	        if (numPeople <= 2) {
	            price = numPeople * 10000;																// 2명까지는 인당 만원
	        } else {
	            price = 2 * 10000 + (numPeople - 2) * 3000;												// 3명부터는 추가 요금 3천원 추가!
	        }
	    }
	    priceElement.textContent = '금액: ' + price.toLocaleString() + '원';								// 출력할 금액
	    priceHiddenElement.value = price; // 숨겨진 필드에 금액 저장
	}
	
	// 초기 금액 설정
	document.getElementById('numberInput').value = 1; // 기본값을 1로 설정
	updatePrice();																						// 함수 동작
	
	// 인원 수 변경 시 금액 업데이트
	document.getElementById('numberInput').addEventListener('input', updatePrice);
	
	$(function() {
		// 날짜 선택기 초기화
		$("#datepicker").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: 0, // 오늘 날짜 이후만 선택 가능
			maxDate: "+1M +10D", // 현재 날짜로부터 1개월 + 10일 이내로 선택 가능
			changeMonth: true, // 월 변경 가능
			changeYear: true, // 연도 변경 가능
			onSelect: function() {
				// 날짜가 선택될 때 모든 체크박스를 초기화
				$("input[type='checkbox']").prop('checked', false);
			}
		});
		
		// 예약하기 버튼 클릭 시 날짜 값을 확인
		$("#reserveButton").on("click", function(event) {
			var selectedDate = $("#datepicker").val(); // 선택된 날짜 가져오기
			if (!selectedDate) {
				alert('날짜를 선택해 주세요!');
			} else {
				console.log('선택된 날짜:', selectedDate); // 콘솔에서 선택된 날짜 확인
				$("form[name='reservation_insert']").submit(); // 폼 제출
			}
		});

		// 체크박스를 하나만 선택하도록 설정
		$(document).on('click', "input[type='checkbox']", function() {
			const checkboxes = $("input[type='checkbox']");
			if (this.checked) {
				checkboxes.not(this).prop('checked', false);
			}
		});

		// 예약하기 버튼 클릭 시 폼 제출
		$("input[name='join']").on("click", function() {
			$("form[name='reservation_insert']").submit();
		});
	});
	
	//임시 로그인------------------------------------
	
	//로그인form 버튼
	let $loginFormButton = $("div[name='login_form']")
	$loginFormButton.on("click", function(){
		window.location.href="/login_form";	
	})
	
	//-----------------------------------------------
	
	//마이페이지---------------------------------------
	let $myPagebutton = $("div[name='myPage']")
	$myPagebutton.on("click", function(){
		window.location.href="/myPage_form";
	})
	//-----------------------------------------------
	
	document.getElementById('numberInput').value = 1; // 기본값을 1로 설정
	
    // 로그아웃
    let $logoutButton = $("input[name='logout']");

	$logoutButton.on("click", function() {
		location.href="/logout";
	})