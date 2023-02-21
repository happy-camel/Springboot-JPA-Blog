package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔할때 @Service체크하며 Bean에 등록해줌(Ioc해준다)
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Integer 회원가입(User user) {
        try{
            userRepository.save(user);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("UserService : 회원가입() : "+ e.getMessage());
        }
        return -1;
    }

    @Transactional(readOnly = true)//Select할 때 트랜잭션 시작, 서비스 종료시에 트랜젝션 종료(정합성)
    public User 로그인(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
