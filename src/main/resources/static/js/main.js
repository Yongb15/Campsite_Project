// 임시 로그인------------------------------------
		let $loginFormButton = $("div[name='login_form']");
		$loginFormButton.on("click", function() {
			window.location.href = "/login_form";
		});

		let $myPagebutton = $("div[name='myPage']");
		$myPagebutton.on("click", function() {
			window.location.href = "/myPage_form";
		});

		let $logoutButton = $("input[name='logout']");
		$logoutButton.on("click", function() {
			location.href = "/logout";
		});

		

	// 이것만 가져가면 됨
	// 예약하기 링크 클릭 시 로그인 여부 확인
	$(document).ready(function() {
		$('#reservation-link').on('click', function(event) {
			const sessionId = $('body').data('session-id'); 			// Thymeleaf로부터 session id 값 읽어오기
			if (!sessionId) {
				event.preventDefault(); // 링크의 기본 동작을 막음
				alert('로그인 후 예약이 가능합니다.');
			}
		});
	});
	
	