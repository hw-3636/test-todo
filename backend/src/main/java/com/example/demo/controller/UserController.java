package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //request : UserDTO - 개발자 직접 정의, 클라이언트에서 받아올 정보 혹은 보낼 User Entity 의 선택 정보를 단순화/구조화
    //response : ResponseEntity - Spring Framework 제공 클래스로, HTTP 응답을 캡슐화

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        //import org.springframework.http.ResponseEntity;
        try {
            if (userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("인자 전달 오류, UserDTO 및 password 확인 요망");
            }
            //request 이용하여 저장할 유저를 Entity 객체에 entity 형식으로 담기
            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))  //사용자 암호 암호화(인코딩)
                    //.password(userDTO.getPassword())
                    .build();

            //service 객체 이용하여 리포지터리에 저장할 유저 담기
            UserEntity registeredUser = userService.create(user);

            //response 객체에 저장된 user 담기
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();

            //저장한 id와 username 를 200 으로 클라이언트에 리턴
            return ResponseEntity.ok().body(responseUserDTO);

        } catch (Exception e) {
            //유저 정보는 항상 하나 -> 리스트로 만들어야 하는 repositoryDTO 사용하지 않고 그냥 userDTO 리턴
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    //로그인
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        log.info("user id: {}", userDTO.getUsername());
        UserEntity user = null;
        try {
            user = userService.getByCredentials(
                    userDTO.getUsername(),
                    userDTO.getPassword(),
                    passwordEncoder);

            log.info("user: {}", user);
        } catch (Exception e) {
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .error("Login failed: 입력 정보 오류. 형식 등 확인 요망")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

        if (user == null) {
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .error("Login failed: 회원 정보가 존재하지 않습니다.")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

        //토큰 생성
        final String token = tokenProvider.create(user);
        log.info("Generated token: {}", token);

        final UserDTO responseUserDTO = UserDTO.builder()
                .username(user.getUsername())
                .id(user.getId())
                .token(token)
                .build();
        return ResponseEntity.ok().body(responseUserDTO);

    }
}
