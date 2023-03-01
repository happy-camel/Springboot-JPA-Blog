package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

//인증 안된 사용자들이 출입할 수 있는 경로 /auth**허용
// 그냥 주소가 /면 index.jsp 이거까지 허용
//static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }
    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }
    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) { //Data리턴 컨트롤러 함수
//        <a>태그로 요청하는건 무조건 get방식..
//        POST 방식으로 key value 데이터 요청
//        라이브러리 - Retrofit2-안드로이드에서 많이씀, OkHttp,RestTemplate
        RestTemplate rt = new RestTemplate();
//        HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

//        HttpBody 오브젝트 생성
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        지금은 하드코딩했지만 변수로 만들어서 쓰는게 좋다
        params.add("grant_type","authorization_code");
        params.add("client_id","99105ac4598408c4951d0a2bec3fa4e8");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code",code);

//        HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//        Http 요청하기-Post방식으로
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        return response.getBody();
    }
    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
