package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
//밑에 3개 어노테이션은 세트다
@Configuration//빈등록 (IOC관리)
@EnableWebSecurity //시큐리티 필터 등록 = 스프링 시큐리티가 활성화 되어있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정주소로 접근시 권한 및 인증 "미리" 체크
public class SecurityConfig {

    @Autowired
    private PrincipalDetailService principalDetailService;

    //해쉬로 암호화
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        String encPassword = new BCryptPasswordEncoder().encode("1234");
        return new BCryptPasswordEncoder();
    }

//    시큐리티가 password인터셉트시 해쉬 이전 정상값을 알아야 같은해쉬로 암호화해서 비교가능
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePwd());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception{
        http.csrf().disable() //csrf토큰 비활성화(테스트시 걸어두는게 좋음)
                .authorizeRequests()
                .antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
                //auth쪽으로 들어오는건 누구나 접근가능
                .permitAll()
                //요청허용
                .anyRequest()
                .authenticated()

               .and()
                .formLogin()
                .loginPage("/auth/loginForm") //인증안된 모든 요청은 로그인폼으로 온다
                .loginProcessingUrl("/auth/loginProc") //로그인수행시
                .defaultSuccessUrl("/");//스프링시큐리티가 해당주소로 오는 로그인 요청을가로채서 대신 로그인(정상적으로 요청완료시)
        return http.build();
    }
}
