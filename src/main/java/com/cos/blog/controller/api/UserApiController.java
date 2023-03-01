package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){ //username, password, email
        System.out.println("UserApiController : save 호출됨");

//        DB에 insert하고 아래에서 return하면 된다

        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
        //자바오브젝트를 JSON으로 변환해서리턴(Jackson라이브러리가 실행)
    }

//      전통적 로그인방식-사용안할꺼. 시큐리티로 할꺼라서
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
//        System.out.println("UserApiController : login 호출됨");
//        User principal = userService.로그인(user); //principal(접근주체)
//
//        if(principal != null){
//            session.setAttribute("principal", principal);
//        }
//
//        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){
        userService.회원수정(user);
//        DB값은 변경됬지만 세션값은 변경되지 않은상태

        //세션등록
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
