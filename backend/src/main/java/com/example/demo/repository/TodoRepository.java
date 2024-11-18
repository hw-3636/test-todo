package com.example.demo.repository;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    //JpaRepository<테이블 매핑 entity 클래스, id 타입>

    List<TodoEntity> findByUserId(String userId);
    //SELECT * FROM Todo WHERE userID = '{userId}'

    //복잡한 쿼리문의 경우 아래와 같이 쿼리문을 직접 지정
    //?1 = userId, ?1(플레이스 홀더)에는 첫번째 매개 변수인 userId 바인딩
    @Query("SELECT t FROM TodoEntity t WHERE t.userId = ?1")
    List<TodoEntity> findByUserIdQuery(String userId);
}
