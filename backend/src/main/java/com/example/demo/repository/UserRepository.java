package com.example.demo.repository;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //Spring Data JPA 제공 쿼리 메서드 사용
    UserEntity findByUsername(String username);  //username 으로 유저 찾기
    Boolean existsByUsername(String username);  //username 으로 유저가 존재하는지 확인
    UserEntity findByUsernameAndPassword(String username, String password);
}
