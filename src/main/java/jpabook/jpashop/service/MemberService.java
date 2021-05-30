package jpabook.jpashop.service;

import jpabook.jpashop.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  //읽기 전용에는 가급적이면 이렇게 쓰자
@RequiredArgsConstructor
public class MemberService {

    //@Autowired
    private final MemberRepository memberRepository;  //변경할 일이 없으면 final을 한다 왜냐? 컴파일 시점ㅇㅔ 체크할 수 있기 때문

    //회원가입
    @Transactional(readOnly = false) //readOnly = false은 쓰기 전용이다. @Transactional은 데이터 변경하는데 있어서 트랜잭션이 꼭 있어야 함 , public 메소드들은 @트랜잭션에 걸림
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
