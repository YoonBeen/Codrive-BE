package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //서비스 어노테이션 (서비스, 컨트롤러, 리포지토리 등 스프링이 인지할 수 있게 다 남겨야 한다.)
public class MemberService {
    private final MemberRepository memberRepository; //저장소에 있는 데이터를 불러와야함
@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*@
        회원가입
        */
    public Long join(Member member) { //회원가입 메서드
        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByName(member.getName()); //이름이 같은 결과값을 찾는다
        result.ifPresent(member1 -> {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        });
    }

    public List<Member> findMembers() {//전체 회원 조회
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
