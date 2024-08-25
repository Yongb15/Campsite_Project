function userDel() {
	let id = $("input[name='myId']").val();
	let password = $("input[name='myPwd']").val();
	let confirmPassword = $("input[name='inPwd']").val();
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
	if(confirm("정말로 회원탈퇴를 하시겠습니까?")) {
	location.href = "/userDelete";
	}
}