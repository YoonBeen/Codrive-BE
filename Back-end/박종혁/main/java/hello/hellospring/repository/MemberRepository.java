package hello.hellospring.repository;

import hello.hellospring.domain.Member;
//회원정보 저장하는 인터페이스
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //멤버의 정보를 저장한다
    Optional<Member> findById(Long id); //멤버의 아이디를 찾는다
    Optional<Member> findByName(String name); //멤버의 이름을 찾는다
    List<Member> findAll(); //모든 회원리스트를 불러온다
}
