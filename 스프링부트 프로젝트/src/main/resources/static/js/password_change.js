function changPassword() {
		
		let id = $("input[name='myId']").val();
	    
	    let password = $("input[name='myPwd']").val();
	    let confirmPassword = $("input[name='inPwd']").val();
	    let newPassword = $("input[name='newPwd']").val();
	    let newPasswordCheck = $("input[name='newPwd2']").val();
	    
		if(confirmPassword == ''){
			alert('현재 비밀번호를 확인하세요')
			$("input[name='inPwd']").focus();
			  	return;
		}   
			   
		if(password != confirmPassword){
			alert('비밀번호가 일치하지 않습니다')
			 return;
		}
	    
		if(newPassword.length < 7) {
			alert('비밀번호는 8자리 이상입니다.')
			return;
		}
	    
 		if(newPassword == ''){
	      alert('새로운 비밀번호를 입력하세요')
	      $("input[name='newPwd']").focus();
	      return;
	    }         
	   
		if(newPasswordCheck == ''){
			alert('새로운 비밀번호를 확인하세요')
			$("input[name='inPwd']").focus();
			      return;
		}
 		
 		
		if(newPassword != newPasswordCheck){
			alert('새로운 비밀번호가 일치하지 않습니다')
			 return;
		}
	
		
	    $.ajax({
	        url: "/changePwd",
	        type: "post",
	        data: JSON.stringify({
	        	id: id,
	        	newPassword: newPassword,
	        }),
	        dataType: "json",
	        contentType: "application/json; charset=utf-8",
	        success: function(data) {
				if(data['param'] == 'success'){
					alert('비밀번호 변경에 성공했습니다')
					window.location.href="/main";
				}
				
				if(data['param'] == 'fail'){
					alert('비밀번호 변경에 실패했습니다')
					return;
				}
	        }
	    });
		
		
	}