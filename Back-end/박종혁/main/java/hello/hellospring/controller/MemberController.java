package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//멤버 컨트롤(회원가입 관련 컨르롤러)
@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired//컨트롤러 생성(실행) 시 스프링 컨테이너에 있는 memberService 객체를 넣어준다(의존성 주입 - 생성자 주입)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new") //회원등록폼 컨트롤러
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {//회원 생성 메서드
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);//할당 받은 회원의 정보를 회원가입 시킨다.
        return "redirect:/"; //다시 홈화면으로
    }
}
