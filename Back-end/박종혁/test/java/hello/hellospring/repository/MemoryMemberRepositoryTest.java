package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach//테스트가 하나 끝날때마다 이 메서드를 수행하라
    public void afterEach(){
        repository.clearStore();
    }
    @Test //테스트케이스 어노테이션
    //테스트케이스는 용량이 큰 메인을 실행해보기 힘들때 사용 (그냥 일반적으로 사용된다)
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();//optional 메서드일 경우 리턴값을 get함수를 사용해서 꺼내줘야 한다
        Assertions.assertEquals(member, result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        Member result = repository.findByName("spring1").get();
        Assertions.assertEquals(member1, result);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        Assertions.assertEquals(result.size(), 2);

    }
}
