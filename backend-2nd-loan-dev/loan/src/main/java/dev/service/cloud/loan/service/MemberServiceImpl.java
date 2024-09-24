package dev.service.cloud.loan.service;

import dev.service.cloud.loan.model.Member;
import dev.service.cloud.loan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    public Member findUserByUsername(String name){
        return memberRepository.findByMemberName(name);
    }

    public void saveUser(Member member) {
        memberRepository.save(member);
    }
}
