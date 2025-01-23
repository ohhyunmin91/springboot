package org.zerock.b01.service;

import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

public interface BoardService {
    // 게시물 등록 서비로 로직 메소드
    Long register(BoardDTO boardDTO);
    // 게시물 1행 조회 서비스로 로직 메소드
    BoardDTO readOne(Long bno);
    // 게시물 1행 수정 서비스로 로직 메소드
    void modify(BoardDTO boardDTO);
    // 게시물 1행 삭제 서비스로 로직 메소드
    void remove(Long bno);

    PageResponseDTO list(PageRequestDTO pageRequestDTO);
}
