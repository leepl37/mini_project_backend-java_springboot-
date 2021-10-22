package chironsoft.test.Dao;

import lombok.Data;

@Data
public class BasketOrder {
    private int pkMemberIdx01;
    private String memberId;
    private int orderCount;
    private String productName;
}
