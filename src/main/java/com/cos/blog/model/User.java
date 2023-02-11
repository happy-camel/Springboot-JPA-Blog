package com.cos.blog.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


//ORM -> JAVA(다른언어) -> OBJACT -> 테이블로 매핑해주는 기술
@Entity //클레스를 테이블화.  프로젝트 실행시 user클레스를 통해 데이터를 읽어서 자동으로 MySQl에 테이블이 생성된다
public class User {
    //프라이머리 키 정의 - 보통 long인데 우린 적으니까 int로

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링전략을 따라간다
    private int id;// 시퀀스, auto_increment
    @Column(nullable = false, length = 30)
    private String username; //아이디
    @Column(nullable = false, length = 100) //123456 - > 해쉬로 비밀번호 암호화
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
    @ColumnDefault("'user'")
    private String role; //회원의 권한(어드민,유저,매니저 등)Enum을 쓰는게 좋다(데이터의 도메인 생성가능)
    @CreationTimestamp //시간 자동입력
    private Timestamp createDate;

    //원래 회원정보 수정하는 업데이트 데이트도 필요한데 우린 크리에이트데이트만 사용

}
