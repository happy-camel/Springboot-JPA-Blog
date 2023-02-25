package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service//Bean 등록
public class PrincipalDetailService implements UserDetailsService {


    @Autowired
    private final UserRepository userRepository;

    public PrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    스프링이 로그인요청시  username, password변수 2개 인터셉트.
//    password 부분 처리는 알아서 함
//    username이 DB에 있는지만 확인해서 리턴 -우리가 처리
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.:"+username));
//        시큐리티 세션에 유저 정보가 저장됨
        return new PrincipalDetail(principal);
    }
}
