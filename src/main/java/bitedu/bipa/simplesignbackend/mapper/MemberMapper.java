package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    Member getMember(int id);

    List<Member> getMemberList();

    int createMember(Member member);

    int updateMember(Member member);

    int deleteMember(int id);
}
