package chironsoft.test.service;

import chironsoft.test.Dao.Member;
import chironsoft.test.domain.model.app.MemberMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void getAllUser() {
        List<Member> allUser = memberService.getAllUser();
        assertEquals(allUser.size(), 3);
    }

    @Test
    public void getMember(){
        Member tony = new Member("tony");
        Member member = memberService.getMemberWithId("tony");
        if(member==null){
            System.out.println("해당 유저가 존재하지 않습니다.");
        }else{
            System.out.println(member.toString());
        }
    }

    @Test
    public void deleteMember(){
        boolean b = memberService.deleteMember("tony02");
        System.out.println(b);
    }

    @Test
    public void insertUser() throws Exception {
        Member member = new Member("tony02", "1");
        boolean b = memberService.insertNewMember(member);
        assertEquals(b, true);
    }
}