package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.mapper.MemberMapper;
import bitedu.bipa.simplesignbackend.model.vo.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public List<Member> getMemberList() {
        return memberMapper.getMemberList();
    }

    public Member getMember(int id) {
        return memberMapper.getMember(id);
    }

    public int createMember(Member member) {
        return memberMapper.createMember(member);
    }

    public int updateMember(Member member) {
        return memberMapper.updateMember(member);
    }

    public int deleteMember(int id) {
        return memberMapper.deleteMember(id);
    }

}
