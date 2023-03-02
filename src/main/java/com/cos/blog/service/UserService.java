package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔할때 @Service체크하며 Bean에 등록해줌(Ioc해준다)
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }

    @Transactional
    public void 회원가입(User user) {

        String rawPassword = user.getPassword(); //1234원문
        String encPassword = encoder.encode(rawPassword); //해쉬화
        user.setPassword(encPassword);
        //디펜던시인젝션받아서 사용
        user.setRole(RoleType.USER);

        userRepository.save(user);
//        try{
//            userRepository.save(user);
//            return 1;
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("UserService : 회원가입() : "+ e.getMessage());
//        }
//        return -1;
    }
//  전통적 로그인방식. 사용안함
//    @Transactional(readOnly = true)//Select할 때 트랜잭션 시작, 서비스 종료시에 트랜젝션 종료(정합성)
//    public User 로그인(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//    }
    @Transactional
    public void 회원수정(User user){
//        수정시에는 Jpa영속성 컨텍스트에 User오브젝트를 영속화시키고
//        영속화된 User오브젝트를 수정
//        select로 db에서가져와서 영속화. 영속화된걸 수정하면 스프링이 자동으로 update문 날려줌
        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });
//        Validate 체크=> oauth 필드에 값이 없으면 수정 가능
        if(persistance.getOauth()==null || persistance.getOauth().equals("")){
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }

        //회원수정함수종료=서비스종료=트잭종료==commit
//        영속화된 persistance객체의 변화가 감지되면 더티체킹 = 자동 update
    }
}
