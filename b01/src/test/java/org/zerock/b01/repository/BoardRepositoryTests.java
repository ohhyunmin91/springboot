package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    // jpa insert 기능 테스트
    @Test
    public void testJpaInsert() {
        // 100개의 임의 board객체를 생성
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            // jpa insert기능으로 board테이블에 데이터 저장
            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }

    // jpa select 기능 테스트(1개 행 가져오기)
    @Test
    public void testSelect() {
        Optional<Board> result = boardRepository.findById(10L); // bno가 10인 행을 가져오기
        Board board = result.orElseThrow();

        log.info(board);
    }

    // jpa select 기능 테스트(전체 행 가져오기)
    @Test
    public void testSelectAll() {
        List<Board> result = boardRepository.findAll();

        result.forEach(item -> log.info(item));
    }

    // jpa update 기능 테스트
    @Test
    public void testUpdate(){
        Optional<Board> result = boardRepository.findById(10L); // bno가 10인 행을 가져오기
        Board board = result.orElseThrow();
        board.change("update..title 10", "update content 10");
        boardRepository.save(board);    // board라는 객체에 id값이 있기 때문에 해당 save는 update
    }

    // jpa delete 기능 테스트(id로 삭제하는 방법)
    @Test
    public void testDelete1(){
        boardRepository.deleteById(1L);
    }

    // jpa delete 기능 테스트(객체로 삭제하는 방법)
    @Test
    public void testDelete2(){
        Optional<Board> result = boardRepository.findById(11L); // bno가 10인 행을 가져오기
        Board board = result.orElseThrow();

        boardRepository.delete(board);
    }
}
