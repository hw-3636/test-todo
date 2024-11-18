package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor  //매개 변수 없는 생성자
@AllArgsConstructor  //모든 변수를 가진 생성자
@Getter
@Setter
@Entity
@Table(name = "Todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //id 생성기 사용하여 자동으로 id 생성, 기본값 AUTO.
    private Long id;
    private String userId;
    private String title;
    private boolean done;

    // JSON 형식으로 id 값을 받아올 때 String 반환
    // String 타입을 Long 타입으로 변경
    @JsonSetter("id")
    public void setIdFromString(String id) {
        this.id = Long.parseLong(id);
    }
}
