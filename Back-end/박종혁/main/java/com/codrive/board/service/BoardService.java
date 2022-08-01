package com.codrive.board.service;


import com.codrive.board.entity.Board;
import com.codrive.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

//서비스 되는 작업들 글작성 게시글 리스트 삭제 수정 등등
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    //글작성
    public void write(Board board, MultipartFile file) throws Exception {//글 작성 + 파일 업로드 할 수 있는 메서드
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";//파일을 저장할 경로
        UUID uuid = UUID.randomUUID(); //식별자 파일이름을 랜덤으로 이름을 정해주는 식별자
        String fileName = uuid + "-" + file.getOriginalFilename(); //uuid라는 랜덤의 숫자가 붙고 뒤에 파일저장이름이 같이 생성된 파일 객체
        File saveFile = new File(projectPath, fileName);//파일을 생성할때 projectpath라는 파일의 경로로 생성하고, name 이라는 이름을 가지는 객체
        file.transferTo(saveFile); //파일 저장을 안하거나 잘못된 파일을 올렸을때 발생된 예외 처리를 해줘야 한다
        board.setFilename(fileName);// 저장된 파일이름을 DB에 저장
        board.setFilepath("/files/" + fileName);//저장된 파일 이름과 파일 경로를 DB에 저장
        boardRepository.save(board);//글을 새로 작성했을 때 저장소에 저장하는 메서드
    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }
//게시글 검색
    //localhost:8080/board/list?searchKeyword =11 을 하면 11이 포함된 게시글을 검색한다
    //localhost:8080/board/list?searchKeyword =11&page=1을 하면 11이 포함된 게시글들 중 첫페이지에 포함되지 않은 2페이지 내용을 보여주는 url이다
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
    //특정 게시글 불러오기
    public Board boardview(Integer id) {
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }
}
