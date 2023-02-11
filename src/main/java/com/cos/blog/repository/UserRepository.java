package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//user테이블을 관리하는 repositoty이고 유저테이블의 pk는 Integer형식이다
//DAO, 자동으로 bean으로 등록되서 @Repository 생략가능 컴포넌트스캔시 유저레포디토리를 메모리에 띄워주는데 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
