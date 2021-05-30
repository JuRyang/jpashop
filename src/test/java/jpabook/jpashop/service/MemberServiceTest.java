package jpabook.jpashop.service;


import jpabook.jpashop.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class) //junit실행 할때 스프링이랑 엮어서 실행할래
@SpringBootTest //스프링컨테이너에서 돌리는 것, 이거 없으면aoutowird 오류
@Transactional //기본적으로 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void 회원가입() throws Exception{

        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);
        //then

        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{

        //given
        Member member1 = new Member();
        member1.setName("Han");

        Member member2 = new Member();
        member2.setName("Han");

        //when
        memberService.join(member1);
        try{
            memberService.join(member2); //예외가 발생해야된다.
        }catch (IllegalArgumentException e){
            return;
        }
        //then
        fail("예외가 발생해야 한다.");
    }




}