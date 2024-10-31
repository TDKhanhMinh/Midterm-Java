package com.trandokhanhminh.e_commerce.reponsitory;

import com.trandokhanhminh.e_commerce.entity.Category;
import com.trandokhanhminh.e_commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByProductId(int productId);

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByOrderBySalePriceDesc();

    List<Product> findAllByOrderBySalePriceAsc();

    List<Product> findAllByOrderByOriginalPriceDesc();

    List<Product> findAllByOrderByOriginalPriceAsc();


    @Query("select p from Product p")
    Page<Product> productPage(Pageable pageable);

    @Query("select p from Product p where p.category.name like '%:search%' or p.name like '%:search%'")
    Page<Product> productSearch(String search, Pageable pageable);

    @Query("select p from Product p where " + "lower(p.name) like lower(concat('%',:searchKey,'%')) or " + "lower(p.category.name) like lower(concat('%',:searchKey,'%'))")
    List<Product> findProductByKey(@Param("searchKey") String keyword);

    @Query("SELECT p FROM Product p where " +
            "(:categoryName is null OR p.category.name = :categoryName) " +
            "and (:type is null OR p.productType = :type)  " +
            "and (:minPrice is null OR p.originalPrice >= :minPrice) " +
            "and (:maxPrice is null OR p.originalPrice <= :maxPrice)")
    List<Product> findAllUser(
            @Param("categoryName") String categoryName,
            @Param("type") String type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);

    @Query("SELECT p FROM Product p where" +
            "(:categoryName is null OR p.category.name = :categoryName)" +
            "order by p.originalPrice desc")
    List<Product> sortProductsByCategoryDESC(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p where" +
            "(:categoryName is null OR p.category.name = :categoryName)" +
            "order by p.originalPrice asc ")
    List<Product> sortProductsByCategoryASC(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p where" +
            "(:categoryName is null OR p.category.name = :categoryName)" +
            "order by p.salePrice desc ")
    List<Product> sortProductsBySalePriceDesc(@Param("categoryName") String categoryName);



}
