package chironsoft.test.service;


import chironsoft.test.Dao.BasketOrder;
import chironsoft.test.Dao.Order;
import chironsoft.test.Dao.CommitOrder;
import chironsoft.test.Dao.Product;
import chironsoft.test.ModelEnum.IncreaseOrDecreaseProduct;
import chironsoft.test.domain.model.app.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    private final ProductService productService;

    public OrderService(OrderMapper orderMapper, ProductService productService) {
        this.orderMapper = orderMapper;
        this.productService = productService;
    }

    /*
    member_idx 와 product idx 그리고 주문한 상품의 개수를 요청받는다.(orderCommit 은 옵션)
    order.commit 값이 1로 들어가면 구매가 되며 order.commit 값이 없으면 장바구니에 담음

    생성자 public Order(int memberIdx, int productIdx, int orderCount, int orderCommit)
    매개변수가 4개 들어가면 구매.
    생성자 public Order(int memberIdx, int productIdx, int orderCount)
    매개변수가 3개 들어가면 장바구니에 담김.
     */
    boolean insertNewOrder(Order order) {

        //상품의 pk 넘버로 상품의 info 를 가져오고 해당 상품의 재고가 주문하는 개수보다 많으면 이상없이 장바구니에 주문
        //order 의 fk 키를 통해 해당 product 의 정보 가져오기.
        Product product = productService.getProduct(order.getFkOrderProductIdx02());

        //상품을 장바구니에 담기 전에, 해당 물품의 재고가 있는지 파악.
        if(product.getProductCount()<order.getOrderCount()){
            System.out.println("재고가 부족합니다.");
        }else{
            //재고 존재. 상품의 키 값 그리고 재고에서 주문 수량을 뺄 거라는 Remove Enum, 그리고 개수 를 넘겨준다
            if(productService.updateProductCount(order.getFkOrderProductIdx02(), IncreaseOrDecreaseProduct.Remove, order.getOrderCount())){
                System.out.println("상품 재고가 수정되었습니다.");
                return orderMapper.insertNewOrder(order);
            }else{
                System.out.println("수정되지 않았습니다.");
            }
        }
        return false;
    }

    //해당 주문 내역을 가져오기 order_idx 값 필요.
    Order getTheOrder(int order_idx){
        return orderMapper.getTheOrder(order_idx);
    }


    /*
    주문 취소, 주문의 idx 값을 받아온다.
     */
    boolean cancelOrder(int order_idx){
        Order theOrder = getTheOrder(order_idx);
        //먼저 Order 테이블에서 해당 주문을 취소한다.
            if(orderMapper.cancelOrder(order_idx)){
                //취소가 완료되면
                //취소 후 장바구니에 담겼던 상품의 수량을 다시 상품 재고에 추가.
                //product 키값, 추가한다는 표시로 Enum(Add)사용, 장바구니에 담겼던 상품의 수량
                if(productService.updateProductCount(theOrder.getFkOrderProductIdx02(), IncreaseOrDecreaseProduct.Add, theOrder.getOrderCount())){
                    //상품 재고에 다시 추가됨.
                    System.out.println("상품을 업데이트 시 문제 발생 함수:cancelOrder");
                    return true;
                }
            }
            //장바구니에 상품이 없음.
            return false;
    }

    //해당 멤버의 장바구니 상품들을 가져온다.
    List<BasketOrder> getBasketOrder(int member_idx){
        return orderMapper.getBasketOrder(member_idx);
    }

    //해당 멤버의 구매확정된 상품들을 가져온다.
    List<CommitOrder> getCommitOrder(int member_idx){
        return orderMapper.getCommitOrder(member_idx);
    }

    //장바구니에 담긴 물품을 구매확정으로 변경한다.
    boolean fromBasketToCommit(int order_idx){
        return orderMapper.fromBasketToCommit(order_idx);
    }
}
