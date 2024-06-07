package mapping.section01.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberRegistService {



    private MemberRepository memberRepository;

    @Autowired
    public MemberRegistService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    @Transactional
    public void registMember(MemberRegistDTO newMember) {

        //받아온 DTO 에 가공을 할 것이 있다면
        // 이공간에서 가공을 한 후에
        // Entity 에 저장을 한다
        Member member = new Member(
                newMember.getMemberId(),
                newMember.getMemberPwd(),
                newMember.getMemberName(),
                newMember.getPhone(),
                newMember.getAddress(),
                newMember.getEnrollDate(),
                newMember.getMemberRole(),
                newMember.getStatus()
        );

        memberRepository.save(member);
    }


    @Transactional
    public String registMemberAndFindName(MemberRegistDTO newMember) {

        registMember(newMember);

        return memberRepository.findNameById(newMember.getMemberId());
    }
}
