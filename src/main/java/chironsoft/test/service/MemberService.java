package chironsoft.test.service;

import chironsoft.test.Dao.Member;
import chironsoft.test.domain.model.app.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    //빈 주입
    final
    MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    //함수 시작

    public List<Member> getAllUser() {
        return memberMapper.getAllUser();
    }

    public Member getMemberWithId(String member_id){
        return memberMapper.getMemberWithId(member_id);
    }

    //멤버가 존재하지 않으면, false 삭제 되면 true.
    public boolean deleteMember(String name) {
        return memberMapper.deleteMember(name);
    }

    //Insert 되면 true, 중복된 멤버가 존재하면 false.
    // memeber id 와 member pw 값을 받아 insert.
    public boolean insertNewMember(Member member) throws Exception {
        try {
            memberMapper.insertNewMember(member);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}


