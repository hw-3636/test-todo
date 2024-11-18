package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //User 생성
    public UserEntity create(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("인자 전달 오류, 매개변수 확인 요망");
        }
        final String username = userEntity.getUsername();
        if (userRepository.existsByUsername(username)) {  //true, 이미 유저가 존재할 시
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(userEntity);
    }

    //사용자 자격 증명
    //BCryptPasswordEncoder 사용하지 않음 -> 같은 값을 인코딩하더라도 할 때마다 값이 다름
    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder) {
        //넘어온 username 기준으로 userEntity 검색
        final UserEntity originalUser = userRepository.findByUsername(username);

        //matches 메서드 -> 넘어온 패스워드와, 넘어온 username 으로 검색한 user 의 패스워드가 같은지 확인
        if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

    //User 자격 증명, username && password 등의 인증 정보를 이용하여 User Entity 조회
    public UserEntity getByCredentials(final String username, final String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
