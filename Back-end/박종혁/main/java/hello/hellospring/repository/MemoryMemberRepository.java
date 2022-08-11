package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository//스프링에 repository라고 알려주는 어노테이션
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>(); //임의의 id와 멤버를 키와 값으로 저장하는 맵함수 생성
    private static long sequence = 0L; //키값을 생성
    @Override
    public Member save(Member member) {
        member.setId(++sequence); //임의의 id를 할당시킨다
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //아이디가 없는 상황에서도 리턴 값 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() //map 전체를 돌면서 맞는 이름을 찾는 람다식
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());//스토어에 있는 값들을 리스트로 반환한다.
    }

    public void clearStore() {
        store.clear();
    }
    }
