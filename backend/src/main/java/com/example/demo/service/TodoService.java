package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    //할일 업데이트(수정)
    public List<TodoEntity> update(final TodoEntity entity) {
        validation(entity);

        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    //새로운 할일 목록 추가
    public List<TodoEntity> create(final TodoEntity entity) { //final 매개변수 할당, 메서드 내부에서 entity 가 변경되지 않을 것을 명시
        validation(entity);

        repository.save(entity);
        log.info("Entity id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    //UserId 검색
    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validation(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity {}", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return retrieve(entity.getUserId());
    }


    //유효성 검사
    //로직이 커지면 TodoValidation.java 파일과 같이 따로 관리하도록.
    private static void validation(TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

    public String testService() {
        TodoEntity entity = TodoEntity.builder().title("first todo item").build();
        repository.save(entity);

        TodoEntity savedEntity2 = repository.findById(entity.getId()).orElseThrow(() -> new EntityNotFoundException("Entity 를 찾을 수 없습니다."));

        return savedEntity2.getTitle();
    }

//    public String testServiceExam() {
//        return "test Service";
//    }
}

