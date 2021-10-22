package chironsoft.test.Dao;

import lombok.Data;

@Data
public class Order {
    private int pkOrder01;
    private int fkOrderMemberIdx01;
    private int fkOrderProductIdx02;
    private int orderCount;
    private int orderCommit;

    Order(){}

    public Order(int pk){
        this.pkOrder01 = pk;
    }

    public Order(int memberIdx, int productIdx){
        this.fkOrderMemberIdx01 = memberIdx;
        this.fkOrderProductIdx02 = productIdx;
        this.orderCount = 0;
        this.orderCommit = 0;
    }

    public Order(int memberIdx, int productIdx, int orderCount){
        this.fkOrderMemberIdx01 = memberIdx;
        this.fkOrderProductIdx02 = productIdx;
        this.orderCount = orderCount;
        this.orderCommit = 0;
    }

    public Order(int memberIdx, int productIdx, int orderCount, int orderCommit){
        this.fkOrderMemberIdx01 = memberIdx;
        this.fkOrderProductIdx02 = productIdx;
        this.orderCount = orderCount;
        this.orderCommit = orderCommit;
    }

}
