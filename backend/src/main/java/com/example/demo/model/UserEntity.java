package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo_users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
//여러 곳에 unique 제약 걸 때 유용, {@UniqueConstraint(columnNames = "username", "email", "userId")}
//단일 칼럼 제약의 경우 @Column(unique = ture) 사용해도 됨
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    //OAuth, SSO 위해 Null 허용
    //controller 에서 필수값으로 지정할 것
    private String password;
    private String role;
    private String authProvider;
}
