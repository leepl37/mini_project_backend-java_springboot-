package chironsoft.test.Dao;

import lombok.Data;

@Data
public class Member {
    int pkMemberIdx01;
    private String memberId;
    private String memberPw;

    public Member(){}

    public Member(String id, String pw) {
        this.memberId = id;
        this.memberPw = pw;
    }

    public Member(String id){
        this.memberId = id;
    }
}
