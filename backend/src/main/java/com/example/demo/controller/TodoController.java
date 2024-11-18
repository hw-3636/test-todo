package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @DeleteMapping  //삭제(최종, 인증된 유저 사용)
    public ResponseEntity<?> deleteTodoList(@AuthenticationPrincipal String userId,
                                            @RequestBody TodoDTO dto) {
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);
            List<TodoEntity> entities = service.delete(entity);  //삭제 로직
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping  //검색(최종, 인증된 유저 사용)
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        List<TodoEntity> entities = service.retrieve(userId);//인증된 아이디 검색
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping  //업데이트(최종, 인증된 유저 사용)
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId,
                                        @RequestBody TodoDTO dto) {
        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(userId);
        List<TodoEntity> entities = service.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping  //등록(최종, 인증된 유저 사용)
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId,
                                        @RequestBody TodoDTO dto) {
        try {
            //요청 dto -> entity 변환
            TodoEntity entity = TodoDTO.toEntity(dto);
            //기존 아이디 초기화
            entity.setId(null);
            //@AuthenticationPrincipal String userId, Authentication Bearer Token 통해 받은 userId 넘기기
            entity.setUserId(userId);
            //넘어온 dto 활용, 서비스를 이용해 Todo entity 생성
            List<TodoEntity> entities = service.create(entity);
            //entities 를 TodoDTO 로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
            //ResponseDTO 를 변환된 dtos 로 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            //초기화가 잘 되면 초기화 된 ResponseDTO 를 반환, 상태코드 ok()
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            //오류 발생 시 확인할 코드, dto 대신 error 메시지 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

//    @DeleteMapping  //삭제(수정 전, 임시 유저 아이디 사용)
//    public ResponseEntity<?> deleteTodoList(@RequestBody TodoDTO dto) {
//        String temporaryUserId = "temporary-user";
//        try {
//            TodoEntity entity = TodoDTO.toEntity(dto);
//            entity.setUserId(temporaryUserId);
//            List<TodoEntity> entities = service.delete(entity);  //삭제 로직
//            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
//            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e) {
//            String error = e.getMessage();
//            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
//            return ResponseEntity.badRequest().body(response);
//        }
//
//    }

//    @GetMapping  //검색(수정 전, 임시 유저 아이디 사용)
//    public ResponseEntity<?> retrieveTodoList() {
//        String temporaryUserId = "temporary-user";  //이 아이디 검색
//        List<TodoEntity> entities = service.retrieve(temporaryUserId);
//        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
//        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
//        return ResponseEntity.ok().body(response);
//    }

//    @PostMapping  //등록(수정 전, 임시 유저 아이디 사용)
//    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
//        try {
//            //임시 유저 아이디
//            String temporaryUserId = "temporary-user";  //이 아이디로 등록
//            //요청 dto entity 변환
//            TodoEntity entity = TodoDTO.toEntity(dto);
//            //기존 아이디 초기화
//            entity.setId(null);
//            //임시 유저 아이디 셋
//            entity.setUserId(temporaryUserId);
//            //넘어온 dto 활용, 서비스를 이용해 Todo entity 생성
//            List<TodoEntity> entities = service.create(entity);
//            //entities 를 TodoDTO 로 변환
//            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
//            //ResponseDTO 를 변환된 dtos 로 초기화
//            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
//            //초기화가 잘 되면 초기화 된 ResponseDTO 를 반환, 상태코드 ok()
//            return ResponseEntity.ok().body(response);
//
//        } catch (Exception e) {
//            String error = e.getMessage();
//            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

//    @GetMapping("/test")
//    public ResponseEntity<?> testTodo() {
//        String str = service.testService();  //테스트 서비스 사용
//        List<String> list = new ArrayList<>();
//        list.add(str);
//        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//
//        return ResponseEntity.ok().body(response);
//    }
}
