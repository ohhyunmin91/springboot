package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Member;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testJpaInsert(){

        IntStream.rangeClosed(1, 50).forEach(i ->{
            Member member = Member.builder()
                    .mid("mid..." + i)
                    .mname("mname..." + i)
                    .mpw("mpw..." + i)
                    .uuid("uuid..." + i)
                    .build();

            Member result = memberRepository.save(member);
            log.info("mid: " + result.getMid());
        });
    }

    @Test
    public void testSelect(){
        String mid = "mid...1";
        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.orElseThrow();

        log.info(member);
    }

    @Test
    public void testUpdate(){
        String mid = "mid...10";
        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.orElseThrow();
        member.change("1234","오리");
        memberRepository.save(member);
    }

    @Test
    public void testDelete1(){
        String mid = "mid...18";
        memberRepository.deleteById(mid);
    }

    @Test
    public void testDelete2(){
        String mid = "mid...24";
        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.orElseThrow();

        memberRepository.delete(member);

    }
}
