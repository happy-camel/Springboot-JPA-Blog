package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
//밑에 3개 어노테이션은 세트다
@Configuration//빈등록 (IOC관리)
@EnableWebSecurity //시큐리티 필터 등록 = 스프링 시큐리티가 활성화 되어있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정주소로 접근시 권한 및 인증 "미리" 체크
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception{
        http.authorizeRequests()
                .antMatchers("/auth/**")
                //auth쪽으로 들어오는건 누구나 접근가능
                .permitAll()
                //요청허용
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm");
        return http.build();
    }
}
