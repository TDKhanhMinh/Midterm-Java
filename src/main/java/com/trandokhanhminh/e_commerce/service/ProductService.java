package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    List<Product> findAllByCategory(String categoryName);

    public void updateProductQuantity(Product product);

    void removeByProductId(int productId);

    void saveProduct(MultipartFile multipartFile, Product product) throws IOException;

    void updateProduct(MultipartFile multipartFile, Product product, Product oldProduct) throws IOException;

    Product findProductByProductId(int productId);

    List<Product> sortProductsByCategoryDESC(String categoryName);

    List<Product> sortProductsByCategoryASC(String categoryName);

    List<Product> sortProductsBySalePriceDesc(String categoryName);

    List<Product> findAllByOrderBySalePriceDesc();

    List<Product> findAllByOrderBySalePriceAsc();

    List<Product> findAllByOrderByOriginalPriceDesc();

    List<Product> findAllByOrderByOriginalPriceAsc();

    Page<Product> pageProduct(int pageNo);


    List<Product> findProductByKey(String brandName);

    List<Product> findAllUser(String categoryName, String type, Double minPrice, Double maxPrice);


}
