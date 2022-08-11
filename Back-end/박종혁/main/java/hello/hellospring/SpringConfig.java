package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//직접 스프링 빈 연동하기
//@Configuration
//public class SpringConfig {
//    @Bean
//    public MemberService memberService(){
//        return new MemberService(memberRepository());
//    }
//    @Bean
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }
//}
