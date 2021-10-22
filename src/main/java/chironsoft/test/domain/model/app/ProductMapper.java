package chironsoft.test.domain.model.app;

import chironsoft.test.Dao.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getAllProduct();

    Product getProduct(int idx);

    boolean insertNewProduct(Product product) throws Exception;

    boolean updateProductCount(Product product);

    boolean deleteProduct(int idx);
}
