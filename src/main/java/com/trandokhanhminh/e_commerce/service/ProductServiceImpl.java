package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Category;
import com.trandokhanhminh.e_commerce.entity.Product;
import com.trandokhanhminh.e_commerce.reponsitory.CategoryRepo;
import com.trandokhanhminh.e_commerce.reponsitory.ProductRepo;
import com.trandokhanhminh.e_commerce.utils.UploadImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Component
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UploadImg uploadImg;

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }


    @Override
    public List<Product> findAllByCategory(String categoryName) {
        Category category = categoryRepo.findByName(categoryName);
        return productRepo.findAllByCategory(category);
    }


    @Override
    public void removeByProductId(int productId) {
        Product product = productRepo.findByProductId(productId);
        product.setCategory(null);
        productRepo.delete(product);
    }

    @Override
    public void saveProduct(MultipartFile multipartFile, Product product) throws IOException {
        Product productNew = new Product();
        if (multipartFile.isEmpty()) {
            productNew.setImageUrl(null);
        } else {
            if (uploadImg.isUploadImage(multipartFile)) {
                System.out.println("Success");
            }
            uploadImg.isUploadImage(multipartFile);
            productNew.setImageUrl(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        }
        productNew.setName(product.getName());
        productNew.setOriginalPrice(product.getOriginalPrice());
        productNew.setProductType(product.getProductType());
        productNew.setCategory(product.getCategory());
        productNew.setQuantity(product.getQuantity());
        productNew.setSalePrice(product.getSalePrice());
        productRepo.save(productNew);
    }

    @Override
    public void updateProductQuantity(Product product) {
        productRepo.save(product);
    }

    @Override
    public void updateProduct(MultipartFile multipartFile, Product product, Product oldProduct) throws IOException {
        if (multipartFile.isEmpty()) {
            oldProduct.setImageUrl(oldProduct.getImageUrl());
        } else {
            if (uploadImg.isUploadImage(multipartFile)) {
                System.out.println("Success");
            }
            uploadImg.isUploadImage(multipartFile);
            oldProduct.setImageUrl(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        }
        oldProduct.setName(product.getName());
        oldProduct.setProductType(product.getProductType());
        oldProduct.setOriginalPrice(product.getOriginalPrice());
        oldProduct.setCategory(product.getCategory());
        oldProduct.setQuantity(product.getQuantity());
        oldProduct.setSalePrice(product.getSalePrice());
        productRepo.save(oldProduct);
    }

    @Override
    public Product findProductByProductId(int productId) {
        return productRepo.findByProductId(productId);
    }

    @Override
    public List<Product> sortProductsByCategoryDESC(String categoryName) {
        return productRepo.sortProductsByCategoryDESC(categoryName);
    }

    @Override
    public List<Product> sortProductsByCategoryASC(String categoryName) {
        return productRepo.sortProductsByCategoryASC(categoryName);
    }

    @Override
    public List<Product> sortProductsBySalePriceDesc(String categoryName) {
        return productRepo.sortProductsBySalePriceDesc(categoryName);
    }

    @Override
    public List<Product> findAllByOrderBySalePriceDesc() {
        return productRepo.findAllByOrderBySalePriceDesc();
    }

    @Override
    public List<Product> findAllByOrderBySalePriceAsc() {
        return productRepo.findAllByOrderBySalePriceAsc();
    }

    @Override
    public List<Product> findAllByOrderByOriginalPriceDesc() {
        return productRepo.findAllByOrderByOriginalPriceDesc();
    }

    @Override
    public List<Product> findAllByOrderByOriginalPriceAsc() {
        return productRepo.findAllByOrderByOriginalPriceAsc();
    }


    @Override
    public Page<Product> pageProduct(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 8);
        return productRepo.productPage(pageable);
    }

    @Override
    public Page<Product> searchProduct(int pageNum, String search) {
        Pageable pageable = PageRequest.of(pageNum, 8);
        return productRepo.productSearch(search, pageable);
    }

    @Override
    public List<Product> findProductByKey(String brandName) {
        return productRepo.findProductByKey(brandName);
    }

    @Override
    public List<Product> findAllUser(String categoryName, String type, Double minPrice, Double maxPrice) {
        return productRepo.findAllUser(categoryName, type, minPrice, maxPrice);
    }


}
