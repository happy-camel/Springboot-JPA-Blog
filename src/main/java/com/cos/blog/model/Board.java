package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int id;
    @Column(nullable = false, length = 100)
    private String title;
    @Lob //대용량 데이터
    private String content;//섬머노트 라이브러리 -> 글이 디자인되서 들어감-> <html>태그가 섞여서 용량이커짐
    @ColumnDefault("0") //기본값
    private int count; //조회수
    @ManyToOne(fetch = FetchType.EAGER) //Many = Board, user = one
    //board 테이블 셀렉시 user정보는 무조건 가져올게 (하나밖에없으니까.) 기본전략
    @JoinColumn(name="userId")
    private User userId; //DB는 오브젝트저장못함. FK, 자바는 오브젝트 저장가능
//    자바에서 데이터베이스 자료형에 맞춰서 테이블 생성해서 원래는키값을(int user)저장해야하는데
//    jpa에서는 그대로 오브젝트 써도됨
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
//     mappedBy= 연관관계의 주인이아니다 (FK가 아니에요) DB에 컬럼만들지마세요
//    나는 그냥 보드 셀렉트할때 조인문으로 값얻기위해 필요한겁니다~
//    mappedBy 뒤에 board는 필드이름 (Reply 클래스의 private Board board; 이부분)
    //조인컬럼은 필요없다 왜? 데이터베이스 제1정규화 위배
    private List<Reply> reply;
    @CreationTimestamp //데이터가 업데이트나 인서트될때 자동으로 현재시간이 들어온다
    private Timestamp createDate;

}
