package chironsoft.test.service;

import chironsoft.test.Dao.Product;
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
public class ProductServiceTest {

    @Autowired private ProductService productService;

    @Test
    public void getAllProduct() {
        List<Product> allProduct = productService.getAllProduct();
        System.out.println(allProduct);
    }

    @Test
    public void insertNewProduct() throws Exception{

        boolean candy = productService.insertNewProduct(new Product("오예스", 100, "빵 부분이 초코파이보다 부드럽고 촉촉한 편이다.[3] 빵 사이에 마시멜로가 아닌 초코크림이 들어있다는 것과 원형이 아닌 사각 모양이라는 것이 특징이다. 표면의 초콜릿 부분은 초코파이나 몽쉘과는 달리 살짝 아삭한 느낌이 있어[4] 이런 느낌을 좋아하는 마니아들도 있다. 전체를 볼 때 초코파이나 몽쉘에 비해 초콜릿 코팅이나 안의 크림은 부족한 반면, 빵의 식감과 맛은 더 좋다. 그러나 가끔 가장자리에 초코크림이 많이 부족한 경우가 있다."));
        assertEquals(candy, true);

//        Product product = productService.getProduct(new Product(1));
//        Product product = new Product(5);
//        boolean b = productService.deleteProduct(product);
//        assertEquals(b, true);
    }

    @Test
    public void getProduct() {
        int count = 201;
        Product product = new Product(1);
        Product product1 = productService.getProduct(1);
        if(product1==null){
            System.out.println("상품이 존재하지 않습니다.");
        }else{
                boolean b = productService.updateProductCount(1, IncreaseOrDecreaseProduct.Remove, count);
                System.out.println("결과:"+b);
            }
        }
//        assertEquals(product1.getProductName(), "초코파이");
}