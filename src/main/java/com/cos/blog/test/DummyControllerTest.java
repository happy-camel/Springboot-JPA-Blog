package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyControllerTest {
    @Autowired //DummyControllerTest가 메모리에 뜰때 얘도 같이 뜬다
    //의존성주입(DI)
    private UserRepository userRepository;
    //http://localhost:8000/blog/dummy/join(요청)
    //http의 body에 username, password, email데이터를 가지고(요청)
    //@RequestParam("username",p~~)요거 생략
    @PostMapping("/dummy/join")
    public String join(User user){ //key=value (약속된규칙)
        System.out.println("id = "+user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("role = "+user.getRole());
        System.out.println("createDate = "+user.getCreateDate());

        user.setRole(RoleType.USER); //디폴트값 회원가입시 넣어준다
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}