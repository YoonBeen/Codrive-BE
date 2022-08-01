package com.codrive.board.repository;

import com.codrive.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
//저장을 할때 게시판의 틀이 되는 인터페이스 JPA사용
//findBy(컬럼이름)Containing 은 컬럼에서 키워드가 포함된 것을 찾겠다
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable); // 저장소에 있는 게시글을 검색하는 메서드 containing은 한글만 포함되도 찾을 수 있게 도와주는 메서드
}
