const $login = $("input[name='loginClick']");
	
	$login.on("click", function() {
		let id = $("input[name='id']").val();
		let pw = $("input[name='password']").val();
		if(id == '') {
			alert('아이디를 입력하세요');
			return;
		}
		if(pw == '') {
			alert('비밀번호를 입력하세요');
			return;
		}
		$.ajax({
			url:'/loginCheck',
			type:'POST',
			data:JSON.stringify({id : id, password : pw}),
			dataType:'json',
			contentType:'application/json; charset=utf-8',
			success: function(data){
				if(data["param"] == 'no'){
					alert('아이디나 비밀번호가 틀렸습니다');
					return;
				} else if(data["param"] == 'yes'){
					location.href = "/main"
				} else {
					alert('에러발생')
					return;
				}
			}
		})
	});