const $id_check = $("input[name='id_check']");
const $signUp_Check = $("input[name='signUpCheck']");
let $b_idCheck = false;

// 아이디 중복 체크
$id_check.on("click", function() {
    let id = $("input[name='id']").val();

    if (id === '') {
        alert("아이디를 입력하세요");
        return;
    } else if (id.length <= 5) {
        alert("아이디는 최소 6자 이상 입력하세요");
        return;
    }

    $.ajax({
        url: "/check_id",
        type: "POST",
        data: JSON.stringify({ id: id }),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            if (data["param"] == 'no') {
                alert('중복된 아이디입니다');
                $b_idCheck = false;
            } else {
                alert('사용 가능한 아이디입니다');
                $b_idCheck = true;
            }
            // 회원가입 버튼의 활성화 상태를 설정
            $signUp_Check.prop('disabled', !$b_idCheck);
        },
        error: function() {
            alert('서버와의 통신 오류');
        }
    });
});

// 회원가입 버튼 클릭 시 폼 제출
$signUp_Check.on("click", function() {
    let email = $("input[name='email']").val();
    let pw = $("input[name='password']").val();
    let $r_pw = $("input[name='r_userPwdInput']").val();
    let id = $("input[name='id']").val();
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (id === '') {
        alert("아이디를 입력하세요");
        return;
    }
    if (id.length <= 5) {
        alert("아이디는 최소 6자 이상 입력하세요");
        return;
    }
    if (pw === '') {
        alert("비밀번호를 입력하세요");
        return;
    }
    if (pw.length < 7) {
        alert("비밀번호는 최소 8자리를 입력하세요");
        return;
    }
    if (pw !== $r_pw) {
        alert("비밀번호 확인이 비밀번호와 다릅니다.");
        return;
    }
    if (email === '') {
        alert("이메일을 입력하세요");
        return;
    }
    if (!emailPattern.test(email)) {
        alert("이메일 형식이 맞지 않습니다.");
        return;
    }
    if (!$b_idCheck) {
        alert('아이디 중복 체크를 먼저 해주세요.');
        return;
    }
    alert('회원가입 되었습니다!')
    $("form[name='signUpPage']").submit();
});
