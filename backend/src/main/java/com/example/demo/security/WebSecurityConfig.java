package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //cors 설정 - 소스 사용
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //From 로그인 방식 disable
                .csrf(AbstractHttpConfigurer::disable)
                //HTTP Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
                //세션 설정 STATELESS
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //oauth2
                //.oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/auth/**").permitAll()
                                .anyRequest().authenticated()
                        );

        //CorsFilter 추가 시 순서 지정
        http.addFilterAfter(
                jwtAuthenticationFilter,  //JWT 인증 필터
                //import org.springframework.web.filter.CorsFilter;
                CorsFilter.class  // CORS 필터 후에 JWT 인증 필터 추가
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        //"Set-Cookie"
        //서버가 클라이언트로 쿠키를 설정할 때 사용하는 헤더 - 사용자 로그인 상태 유지 등 세션 관리를 위해 쿠키 정보 전달

        //"Authorization"
        //클라이언트가 서버로 요청을 보낼 때 자격증명 포함하는 헤더
        //Bearer 토큰 or Basic 인증 방식 사용 -> 우리는 Bearer 사용함
        configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));
//        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
//        configuration.setExposedHeaders(Collections.singletonList("Authorization"));


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
