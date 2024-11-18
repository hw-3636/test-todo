package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {
    // 화면에 보여줄 응답 객체
    private String error;
    private List<T> data;

}
