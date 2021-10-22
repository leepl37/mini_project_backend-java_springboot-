package chironsoft.test.service;

import chironsoft.test.Dao.Product;
import chironsoft.test.ModelEnum.IncreaseOrDecreaseProduct;
import chironsoft.test.domain.model.app.MemberMapper;
import chironsoft.test.domain.model.app.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    private final ProductMapper productMapper;


    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    //모든 상품을 가져온다.
    public List<Product> getAllProduct() {
        List<Product> allProduct = productMapper.getAllProduct();
        return allProduct;
    }

    //상품추가: 상품이름, 개수, 정보(상품설명) / 상품이름은 유니크, 중복된 값이 존재할 시 Exception -> return false
    boolean insertNewProduct(Product product) throws Exception{
        try {
            productMapper.insertNewProduct(product);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    //특정 상품의 정보를 idx 를 통해 조회한다. 존재하지 않으면 null 값 반환 DB 에선 Error 던지지 않음.
    Product getProduct(int idx) {
        return productMapper.getProduct(idx);
    }


    /*
    상품의 개수를 업데이트 한다. 장바구니에 담을 시엔 IncreaseOrDecreaseProduct(Enum) 이 재고에서 줄어야 하기 때문에 Remove 로 표시,
    반대로 장바구니에 있는 상품을 취소할 시엔 Enum 이 Add 가 된다. count 는 상품의 개수를 표기한다.
    insertNewOrder 함수와 cancelOrder 함수에서 호출된다.
     */
    boolean updateProductCount(int product_idx, IncreaseOrDecreaseProduct which, int count) {

        //상품의 idx를 통해 해당 상품을 조회.
        Product product = productMapper.getProduct(product_idx);

        System.out.println("product_info:" + product.toString());

        //해당 상품의 재고 수량.
        int productCount = product.getProductCount();

        //상품의 재고 수량에서 추가할 것인지 뺄 것인지 Enum 을 통해 검사.
        if(which.name().equals("Add")){
            //추가한다면, setProductCount 를 통해, 현재 재고 수량과 장바구니에서 취소한 수량을 더한다
            product.setProductCount(productCount + count);
            System.out.println("상품 취소, 상품 개수 추가:" + product.getProductCount());
        }else{
            //장바구니에 담는다면, setProductCount 를 통해, 현재 재고 수량과 장바구니에서 취소한 수량을 뺀다.
            //재고 수량과 주문 수량 검사는 insertNewOrder 함수에서 구현, 미리 검사함.
            product.setProductCount(productCount - count);
            System.out.println("상품 주문, 상품 개수 감소:" + product.getProductCount());
        }

        //최종 수량 업데이트.
        return productMapper.updateProductCount(product);
    }

    boolean deleteProduct(int product_idx){
        return productMapper.deleteProduct(product_idx);
    }
}

