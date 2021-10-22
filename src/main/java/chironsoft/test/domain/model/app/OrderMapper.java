package chironsoft.test.domain.model.app;


import chironsoft.test.Dao.BasketOrder;
import chironsoft.test.Dao.Order;
import chironsoft.test.Dao.CommitOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {


    Order getTheOrder(int order_idx);

    boolean insertNewOrder(Order order);

    boolean cancelOrder(int order_idx);

    //장바구니에 있는 상품을 order idx 를 통해 구매확정으로 바꾸기.
    boolean fromBasketToCommit(int order_idx);

    //멤버 idx 를 통해 장바구니에 담긴 물품 가져오기.
    List<BasketOrder> getBasketOrder(int member_idx);

    //멤버 idx 를 통해 구매한 물품 가져오기.
    List<CommitOrder> getCommitOrder(int member_idx);
    /*

    멤버가 장바구니에 담은 목록 보여주기. 멤버 pk 값으로 주문한 리스트를 다 가져온 후, 해당 주문의 상품 pk 값을 통해 상품명 가져오기.
        SELECT pk_member_idx_01, member_id, order_count, product_name
        FROM tb_member AS m
        INNER JOIN tb_order AS o
        ON m.PK_MEMBER_IDX_01 = o.FK_ORDER_MEMBER_IDX_01
        INNER JOIN tb_product AS p
        ON p.PK_PRODUCT_IDX_01 = o.FK_ORDER_PRODUCT_IDX_02
        WHERE o.FK_ORDER_MEMBER_IDX_01 = 14;

    멤버가 구매한 목록 보여주기.
        SELECT pk_member_idx_01, member_id, order_count, product_name
        FROM tb_member AS m
        INNER JOIN tb_order AS o
        ON m.PK_MEMBER_IDX_01 = o.FK_ORDER_MEMBER_IDX_01
        INNER JOIN tb_product AS p
        ON p.PK_PRODUCT_IDX_01 = o.FK_ORDER_PRODUCT_IDX_02
        WHERE o.FK_ORDER_MEMBER_IDX_01 = 14 and o.ORDER_COMMIT=1;

    멤버가 물품 장바구니에 담기 구현 완료.
    멤버가 물품 구매하기 구현 해야함. commit 값을 1 한 후 업데이트 처리하기. (기존 함수이용하여 쿼리문만 추가하기)
     */
}
