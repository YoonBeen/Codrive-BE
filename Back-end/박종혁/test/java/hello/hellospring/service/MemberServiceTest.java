package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService ;
    MemoryMemberRepository memberRepository ;
    @BeforeEach
    public void beforEach(){ //DI 기술 데이터베이스가 테스트 코드와 메인 코드에 각각생기는 것을 방지하기 위한 의존성 기술
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore(); //한 메서드 수행 마다 기록되거나 저장되어있던 모든 데이터를 없앤다
    }

    @Test
    void join() {//회원가입 테스트 코드
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);
        //then
        Member findmember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findmember.getName());
    }

    @Test
    void duplicatejoin() {//회원가입 중복 시 발생하는 예외 테스트 코드
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//두개의 이름이 같을 때 오류 발생
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}