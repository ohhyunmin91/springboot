package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Todo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void TestJpaInsert(){

        IntStream.rangeClosed(1, 100).forEach(i ->{
            Todo todo = Todo.builder()
                    .title("title...." + i)
                    .writer("writer...." + i)
                    .finished(false)
                    .build();

            Todo result = todoRepository.save(todo);
            log.info("tno: " + result.getTno());
        });

    }

    @Test
    public void TestSelect(){
        Optional<Todo> result = todoRepository.findById(10L);
        Todo todo = result.orElseThrow();

        log.info(todo);
    }

    @Test
    public void TestSelectALL(){
        List<Todo> result = todoRepository.findAll();
        result.forEach(item -> log.info(item));

    }

    @Test
    public void TestUpdate(){
        Optional<Todo> result = todoRepository.findById(10L);
        Todo todo = result.orElseThrow();
        todo.change("title update",true);
        todoRepository.save(todo);
    }

    @Test
    public void TestDelete1(){
        todoRepository.deleteById(11L);
    }

    @Test
    public void TestDelete2(){
        Optional<Todo> result = todoRepository.findById(15l);
        Todo todo = result.orElseThrow();
        todoRepository.delete(todo);
    }
}
