//입력한 비밀번호와 원본 비밀번호를 비교
let $pwd = /*[[${vo.pwd}]]*/"" //원본비밀번호
let $idx = /*[[${vo.idx}]]*/"" //글번호
$("#del").on('click',function(){
	if(!confirm("삭제하시겠습니까?")){
		return;
	}
		
//입력한 비밀번호
let $c_pwd = $("input[type='password']").val();
	if($pwd != $c_pwd){
		alert("비밀번호 불일치");
			return
	}
	//ajax를 이용해서 idx를 json으로 보내고 수정하고 목록으로 돌아가기
	//url : /board/del -> 제목:deleted , 작성자 : unknown으로 수정하기
	//type : POST
	//data : idx
	//dataType : json
	//contentType :"application/json; charset=utf-8"
	$.ajax({
		url : '/del',
		type : 'POST',
		data : JSON.stringify({idx : $idx}),
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success : function(data){
			alert("삭제성공")	
			window.location.href="/events_board";
		}
	})
})