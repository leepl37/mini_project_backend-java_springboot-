package chironsoft.test.domain.model.app;

import chironsoft.test.Dao.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<Member> getAllUser();

    Member getMemberWithId(String member_id);

    Member getMember(int member_idx);

    boolean deleteMember(String name);

    boolean insertNewMember(Member member) throws Exception;
}
