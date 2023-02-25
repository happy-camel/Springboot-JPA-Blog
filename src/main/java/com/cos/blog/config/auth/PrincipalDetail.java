package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 로그인 완료시 UserDetails타입의 PrincipalDetail을
// 스프링 시큐리티 세션저장소에 저장
@Getter
public class PrincipalDetail implements UserDetails { //implements시 오버라이드 해줘야됨(재정의)
    private User user; //콤포지션(객체를 품고있는것). extends는 상속

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정만료 확인(true=만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정 잠긴지 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //비밀번호 만료 확인(true = 만료안됨. false면 로그인이 안된다)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정 활성상태인지 확인
    @Override
    public boolean isEnabled() {
        return true;
    }
    //계정 권한 리턴. grantedauthority상속한 컬랙션타입으로 리턴해줘야됨
//    권한이 여러개면 for문 돌아야되는데 우린 한개만
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_"+user.getRole(); //ROLE_USER (스프링 리턴 약속)
//            }
//        });
//        람다식으로 표현
        collectors.add(()->{return "ROLE_"+user.getRole();});
        return collectors;
    }
}
