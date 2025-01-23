package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;

import java.util.*;
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

    // JPA paging 기능 테스트
    @Test
    public void testPaging() {
        // 1 page에 게시물 번호 내림차순으로 정렬
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: "+result.getTotalElements());
        log.info("total pages:" +result.getTotalPages());
        log.info("page number: "+result.getNumber());
        log.info("page size: "+result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));
    }

    // JPA Query Method 테스트1
    @Test
    public void queryMethodTest1() {
        List<Board> boardList
                = boardRepository.findByTitleAndWriter("title...99", "user9");

        boardList.forEach(board -> log.info(board));
    }
    // JPA Query Method 테스트2
    @Test
    public void queryMethodTest2() {
        List<Board> boardList
                = boardRepository.findByWriterIn(Arrays.asList("user9", "user3"));

        boardList.forEach(board -> log.info(board));
    }
    // JPA Query Method 테스트3
    @Test
    public void queryMethodTest3() {
        // 1 page에 게시물 번호 내림차순으로 정렬
        Pageable pageable
                = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> boardPage
                = boardRepository.findByTitleContainingOrderByBnoDesc("content", pageable);

        boardPage.forEach(board -> log.info(board));
    }

    // 내가 만든 테스트
    @Test
    public void queryMethodTest4() {
        List<Board> boardList
                = boardRepository.findByTitleOrWriter("title...99", "user3");

        boardList.forEach(board -> log.info(board));
    }

    @Test
    public void queryMethodTest5() {
        List<Board> boardList
                = boardRepository.findByWriterNotLike("%user2%");

        boardList.forEach(board -> log.info(board.toString()));
    }

    @Test
    public void queryMethodAnnotationTest1() {
        // 1 page에 게시물 번호 내림차순으로 정렬
        Pageable pageable
                = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> boardPage
                = boardRepository.findKeyword("content", pageable);

        boardPage.forEach(board -> log.info(board));
    }

    @Test
    public void queryMethodAnnotationTest2() {

        List<Board> boardList
                = boardRepository.findFromTitleWriter("title...14", "user4");

        boardList.forEach(board -> log.info(board));
    }

    @Test
    public void queryMethodAnnotationTest3() {

        List<Board> boardList
                = boardRepository.findFromWriters(Arrays.asList("user1","user2"));

        boardList.forEach(board -> log.info(board));
    }
}
