let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{//function(){}안쓰고 ()=>{}쓰는건 this를 바인딩하기 위해서
            this.save();
        });
    },

    save: function(){
//        alert('user의 save함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

//        console.log(data);
//        ajax 호출시 default가 비동기 호출
//        ajax 통신을 이용해서 3개의 데이터를 json으로 변경해서 insert 요청
//          데이터타입을 지정안해주면 ajax가 자동으로 자바오브젝트로 변환해줌
        $.ajax({
            type:"POST",
            url:"/auth/joinProc",
            data: JSON.stringify(data),
            contentType:"application/json; charset=utf-8",//body데이터가 어떤타입인지(MIME)
            dataType:"json"//응답은 기본적으로 버퍼로와서 String인데, 생긴게 JSON이면 javascript오브젝트로 변경해~
        //회원가입 수행 요청
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
//            console.log(resp);
            location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
}

index.init();