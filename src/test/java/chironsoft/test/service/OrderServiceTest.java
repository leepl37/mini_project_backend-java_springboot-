package chironsoft.test.service;

import chironsoft.test.Dao.*;
import chironsoft.test.ModelEnum.IncreaseOrDecreaseProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Test
    public void insertNewOrder() {
        int count = 12;
        Member member_tony = memberService.getMemberWithId("jena");
        Product product = productService.getProduct(4);
        Order order = new Order(member_tony.getPkMemberIdx01(), product.getPkProductIdx01(), count, 1);
        boolean b = orderService.insertNewOrder(order);
        System.out.println(b);
    }

    @Test
    public void getTheOrder() {

    }

    @Test
    public void cancelOrder() {
        if(orderService.cancelOrder(6)){
            System.out.println("주문이 취소되었습니다.");
        }else{
            System.out.println("주문이 취소되지 않았습니다.");
        }
    }

    @Test
    public void getBasketOrder(){
        List<BasketOrder> basketOrder = orderService.getBasketOrder(14);
        basketOrder.forEach(System.out::println);
    }

    @Test
    public void getCommitOrder(){
        List<CommitOrder> commitOrder = orderService.getCommitOrder(15);
        commitOrder.forEach(System.out::println);
    }

    @Test
    public void fromBasketToCommit(){
        assertEquals(true,orderService.fromBasketToCommit(9));
    }
}
