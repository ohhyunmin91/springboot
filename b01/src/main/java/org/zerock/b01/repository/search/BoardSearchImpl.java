package org.zerock.b01.repository.search;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardSearchImpl implements BoardSearch {
    private final EntityManager entityManager;

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        // JPQL로 작성
        StringBuilder jpql = new StringBuilder("SELECT b FROM Board b WHERE b.bno > 0");

        // 동적 검색 조건 추가
        if ((types != null && types.length > 0) && keyword != null) {
            jpql.append(" AND (");

            for (int i = 0; i < types.length; i++) {
                String type = types[i];
                switch (type) {
                    case "t":
                        jpql.append("b.title LIKE :keyword");
                        break;
                    case "c":
                        jpql.append("b.content LIKE :keyword");
                        break;
                    case "w":
                        jpql.append("b.writer LIKE :keyword");
                        break;
                }

                // 각 조건 사이에 "OR" 추가
                if (i < types.length - 1) {
                    jpql.append(" OR ");
                }
            }
            jpql.append(")");
        }

        // JPQL로 쿼리 생성
        TypedQuery<Board> query = entityManager.createQuery(jpql.toString(), Board.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(
                jpql.toString().replace("SELECT b", "SELECT COUNT(b)"), Long.class
        );

        // 파라미터 바인딩
        if ((types != null && types.length > 0) && keyword != null) {
            query.setParameter("keyword", "%" + keyword + "%");
            countQuery.setParameter("keyword", "%" + keyword + "%");
        }

        // 페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // 결과 조회
        List<Board> list = query.getResultList();
        long count = countQuery.getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}
