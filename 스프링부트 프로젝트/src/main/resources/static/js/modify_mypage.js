function fSubmit() {
	    let id = $("input[name='myId']").val();
	    let nickName = $("input[name='myName']").val();
	    let email = $("input[name='myEmail']").val();
	    let birth = $("input[name='myBirth']").val();
	    let phoneNumber = $("input[name='myPhone']").val();
	    let password = $("input[name='myPwd']").val();
	    let confirmPassword = $("input[name='inPwd']").val();
	   
	    let emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	   
	   if(nickName == ''){
	      alert('이름 또는 별명을 입력하세요')
	      $("input[name='myName']").focus();
	      return;
	   }
	   
	   if(email == ''){
	      alert('이메일을 입력하세요')
	      $("input[name='myEmail']").focus();
	      return;
	   }
	   
	   if(!emailRule.test(email)){
	      alert('이메일을 형식에 맞게 입력하세요')
	      return;
	   }
	   
	   
	   if(phoneNumber == ''){
	      alert('전화번호를 (-)포함 입력하세요')
	      $("input[name='myPhone']").focus();
	      return;
	   }      
	   
	   
	   if(password == ''){
	      alert('비밀번호를 입력하세요')
	      $("input[name='myPwd']").focus();
	      return;
	   }         
	   
	   if(confirmPassword == ''){
	      alert('비밀번호 확인을 입력하세요')
	      $("input[name='inPwd']").focus();
	      return;
	   }   
	   
	   if(password != confirmPassword){
	      alert('비밀번호가 일치하지 않습니다')
	      return;
	   }
	    
	    
	    $.ajax({
	        url: "/userSave",
	        type: "post",
	        data: JSON.stringify({
	            id: id,
	            nickName: nickName,
	            email: email,
	            birth: birth,
	            phoneNumber: phoneNumber,
	            password: password
	        }),
	        dataType: "json",
	        contentType: "application/json; charset=utf-8",
	        success: function(data) {
	           if(data['param'] == 'success'){
	            alert('수정 확인')
	            window.location.href="/main";
	         }
	         
	         if(data['param'] == 'fail'){
	            alert('수정되지 않았습니다')
	            return;
	         }
	        }
	    });
	}