package chironsoft.test.Dao;

import lombok.Data;

@Data
public class Product {
    private int pkProductIdx01;
    private String productName;
    private int productCount;
    private String productDetail;

    public Product(String name, int count, String detail) {
        this.productName = name;
        this.productCount = count;
        this.productDetail = detail;
    }

    Product(){}

    Product(String name){
        this.productName = name;
    }
    public Product(int idx){
        this.pkProductIdx01 = idx;
    }

}
