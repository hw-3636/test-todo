package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import com.example.demo.dto.TodoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")  //'리소스(Resource, 자원) 식별자'를 이용하여 어떤 자원에 접근할 것인지 구분, endPoint 설정
public class TestController {

    @GetMapping
    public String test() {
        return "HelloWorld";
    }

    @GetMapping("/getMapping")
    public String testMapping() {
        return "TestMapping";
    }

    @GetMapping("/{id}")
    public String testPathVariable(@PathVariable(required = false) long id) {
        //required = false: 이 매개변수가 필수적이지 않다는 뜻으로 url 에 명시하지 않아도 에러가 나지 않음.
        return "Hello World Id: " + id;
    }

    @GetMapping("/requestParam")
    public String testRequestParam(@RequestParam(required = false) long id) {
        return "Hello World Request Param Id: " + id;
    }

    @GetMapping("/requestBody")
    public String testRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World Id: " + testRequestBodyDTO.getId() + " body: " + testRequestBodyDTO.getMessage();
    }

    @GetMapping("responseBody")
    public ResponseDTO<String> testResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("responseBody test success");
        return ResponseDTO.<String>builder().data(list).build();
    }

    @GetMapping("responseEntity")
    public ResponseEntity<?> testResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("responseEntity test success, And get 400");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        //badRequest() : 400 Error
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("todoEntity")  //세팅하지 않음
    public ResponseEntity<?> testTodoResponseEntity() {
        boolean isDone = new TodoDTO().isDone();
        TodoDTO response = TodoDTO.builder().done(isDone).build();
        return ResponseEntity.ok().body(response);
    }
}
