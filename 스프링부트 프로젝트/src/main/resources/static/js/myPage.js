//회원정보 수정하기
const $modiButton = $("a[name='modify_mypage']")
$modiButton.on("click", function(){
	window.location.href="/modify_mypage";
})
	
//회원 탈퇴하기
const $delButton = $("a[name='userDel']")
$delButton.on("click", function(){
	window.location.href="/delete_form";
})
	
//비밀번호 변경하기
const $newPwdButton = $("a[name='newPwd']")
$newPwdButton.on("click", function(){	
	window.location.href="/newPassword";
})
	
	
	