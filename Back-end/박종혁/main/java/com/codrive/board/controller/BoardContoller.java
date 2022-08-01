package com.codrive.board.controller;

import com.codrive.board.entity.Board;
import com.codrive.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//실행이 되는 엔진 집합
@Controller
public class BoardContoller {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //해당 url로 자동 완성시켜준다 ex)localhost:8080/board/write
    public String boardWriteForm() {
        return "boardwrite";

    }

    @PostMapping("/board/writepro") //post형식으로 글작성하는 메서드
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {//Board 라는 클래스의 내용을 받을 수 있다.


        boardService.write(board, file);//새롭게 작성한 게시판을 서비스에 적용시킨다.
        model.addAttribute("message", "글 작성하셨습니다.");//message.html 파일을 실행시킨다.
        model.addAttribute("searchUrl", "/board/list");//message.html로 넘긴다.
        return "message";
    }

    //리스트 페이징 원리 localhost:8080/board/list?page=1&size=10 -> 1페이지에 총 10개의 게시글을 보여주겠다 라는 뜻
    @GetMapping("/board/list") //게시글 리스트를 반환하는 메서드
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {
        Page<Board> list = null;
        if (searchKeyword == null) {
            list = boardService.boardList(pageable); //검색한 단어가 없을떄는 그냥 리스트를 보여줌
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);//검색한 단어가 있었을 때 그 단어를 포함한 리스트를 보여준다
        }

    //model 은 다시 넘겨주는 매개변수이다. @PageableDefault는 페이지처리 할 수 있는 어노테이션
    // page가 0인 이유는 1페이지가 0으로 인식한다. size는 10개의 게시글 sort는 정렬 기준  direction은 정렬 순서이다 DESC는 역순으로 정렬 즉 최근에 작성된 게시글로 저장된다.
    int nowPage = list.getPageable().getPageNumber() + 1; //현재페이지 = pageable 에서 넘어온 현재페이지를 할당한다. pageable에서 시작하는 1페이는 0이므로 1을 더해줘야 일반적인 1페이지로 호출 가능하다
    int startPage = Math.max(nowPage - 4, 1); //블럭에서 보여줄 시작 페이지 만약 nowpage가 1보다 작으면 음수가 되니까 최댓값 함수를 넣어준다
    int endPage = Math.min(nowPage + 5, list.getTotalPages()); //블럭에서 보여줄 마지막 페이지 / 지금 만들고 있는 게시글의 최대 페이지가 10이므로 9를 넘지 않게 설정한다

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return"boardlist";
}

    @GetMapping("/board/view") // ex) localhost:8080/board/view?id=1 일때 게시글 1번을 불러올수 있다. (파라미터 / get 방식)
    public String boardview(Model model, Integer id) {
        model.addAttribute("board", boardService.boardview(id));
        return "boardview";
    }

    @GetMapping("/board/delete") // 게시글을 삭제할때 일어나는 메서드 ex)localhost:8080/board/delete?id=1 을 했을 때 id 1번의 게시글이 삭제됨
    public String boardDelete(Integer id) {
        boardService.boardDelete(id);
        return "redirect:/board/list"; //게시글 삭제 후 다시 게시글 리스트로 돌아가기 위한 작업
    }

    @GetMapping("/board/modify/{id}")
    public String boardmodify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardview(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception {

        Board boardTemp = boardService.boardview(id);//기존에 있던 게시글 리스트를 불러온다
        boardTemp.setTitle(board.getTitle());//기존에 있던 게시글의 제목과 내용을 수정한 게시글로 바꾸는 작업 set
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file); // 새롭게 바뀐 게시글을 다시 서비스로 보낸다

        model.addAttribute("message", "수정하셨습니다.");//message.html 파일을 실행시킨다.
        model.addAttribute("searchUrl", "/board/list");//message.html로 넘긴다.
        return "message";
    }
}
