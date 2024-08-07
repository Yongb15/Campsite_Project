document.addEventListener('DOMContentLoaded', function() {
    const slides = document.querySelector('.slides');
    const totalImages = slides.querySelectorAll('img').length;
    let currentIndex = 0;
    const transitionDuration = 500; // 슬라이드 전환 시간 (밀리초 단위)

    function moveToSlide(index) {
        if (index >= totalImages) {
            index = 0;
        } else if (index < 0) {
            index = totalImages - 1;
        }

        const offset = -100 * index;
        slides.style.transition = `transform ${transitionDuration}ms ease-in-out`;
        slides.style.transform = `translateX(${offset}%)`;
        currentIndex = index;
    }

    const nextButton = document.querySelector('.next');
    const prevButton = document.querySelector('.prev');

    nextButton.addEventListener('click', function() {
        moveToSlide(currentIndex + 1);
    });

    prevButton.addEventListener('click', function() {
        moveToSlide(currentIndex - 1);
    });
});



    // 날씨 정보를 가져오는 부분
    fetch('https://api.weatherapi.com/v1/current.json?key=YOUR_ACTUAL_API_KEY&q=Seoul')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // 날씨 정보를 페이지에 표시
            document.getElementById('weather-info').textContent = `${data.current.temp_c}°C, ${data.current.condition.text}`;
        })
        .catch(error => {
            // 날씨 정보 로딩 실패 시 메시지 표시
            console.error('There was a problem with the fetch operation:', error);
            document.getElementById('weather-info').textContent = '날씨 정보를 가져올 수 없습니다.';
        });