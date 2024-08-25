const $submitForm = $("input#send_check");
$submitForm.on("click",function(event){
    event.preventDefault();   
    
    var formData = {
             subject: $('#subject').val(),
             name: $('#name').val(),
             content: $('#content').val(),
             pwd: $('#pwd').val()
         };

	 var isFormValid = true;
		for (var key in formData) {
			if (formData[key].trim() === '') {
				isFormValid = false;
				break;
			}
		}
		if (!isFormValid) {
			alert('모든 필드를 입력해 주세요.');
			window.location.href = '/insert_form?page=' + $('#page').val();
			return;
		}
		        $.ajax({
		            url: '/insert?page=' + $('#page').val(),
		            type: 'POST',
		            contentType: 'application/json',
		            data: JSON.stringify(formData),
		            dataType: 'json',
		            success: function(response) {
		                console.log("Response received:", response);

		                var responseObj = response;

		         if (responseObj.param === 'no') {
		            alert('입력값이 올바르지 않습니다.');
		            window.location.href = '/insert_form?page=' + $('#page').val();
		        } else if (responseObj.param === 'yes') {
		            alert('등록 성공!');
		            window.location.href = '/events_board?page=' + $('#page').val();
		        } else {
		        	alert('서버 오류 발생.');
		    	}
		    },
		    error: function(xhr, status, error) {
		    console.log("AJAX Error:", status, error);
		 	alert('서버와의 통신 중 오류가 발생했습니다.');
		}
	});
});
