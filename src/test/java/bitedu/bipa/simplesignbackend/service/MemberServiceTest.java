package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.model.vo.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @BeforeEach
     void before() {
        Member member = new Member();
        member.setName("Test");
        member.setId(1);

        memberService.createMember(member);
    }

    @AfterEach
    void after() {
        memberService.deleteMember(1);
        memberService.deleteMember(2);
    }

    @Test
    @DisplayName("생성")
    void test3() {
        Member member = new Member();
        member.setName("Tom");
        member.setId(2);

        int insertCnt = memberService.createMember(member);
        assertThat(insertCnt).isEqualTo(1);
    }
    @Test
    @DisplayName("전체 조회")
    void test1() {
        List<Member> memberList = memberService.getMemberList();
        for (Member member : memberList) {
            System.out.println("member = " + member.toString());
        }
    }

    @Test
    @DisplayName("단건 조회")
    void test2() {
        Member member = memberService.getMember(1);
        System.out.println("member = " + member.toString());
    }



    @Test
    @Commit
    @DisplayName("삭제")
    void test4() {
        int deleteCnt = memberService.deleteMember(1);
        assertThat(deleteCnt).isEqualTo(1);
    }

    @Test
    @Commit
    @DisplayName("업데이트")
    void test5() {
        Member member = new Member();
        member.setId(1);
        member.setName("John");
        int updateCnt = memberService.updateMember(member);
        assertThat(updateCnt).isEqualTo(1);
    }
}