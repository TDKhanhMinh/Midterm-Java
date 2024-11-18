package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.Category;
import com.trandokhanhminh.e_commerce.entity.Product;
import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.service.CategoryService;
import com.trandokhanhminh.e_commerce.service.ProductService;
import com.trandokhanhminh.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @GetMapping("/home-page")
    public String showHome(Model model) {
        List<Category> categoryList = categoryService.findALL();
        List<Product> products = productService.findAllProducts();
        List<Product> result = productService.findAllProducts();
        Collections.shuffle(result);
        List<Product> randomProducts = result.stream()
                .limit(20)
                .toList();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("products", products);
        model.addAttribute("productsSuggest", randomProducts);
        return "user-temple/home";
    }

    @GetMapping("/uploadHome")
    public String showUploadHome() {
        return "redirect:/home-page";
    }

    @GetMapping("/productDetails")
    public String showProductDetails(Principal principal, Model model, @RequestParam(value = "productId") int productId) {
        Product product = productService.findProductByProductId(productId);
        User user = userService.findCustomerByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("product", product);
        return "another-temple/product_details";
    }

    @GetMapping("/allProducts")
    public String showAllProducts(Model model, @RequestParam("brandName") String brandName) {
        List<Product> result = productService.findAllProducts();
        Collections.shuffle(result);
        List<Product> randomProducts = result.stream()
                .limit(20)
                .toList();
        if (brandName.equals("all")) {
            model.addAttribute("brandName", "Xu Hướng");
            model.addAttribute("products", randomProducts);
        } else {
            List<Product> products = productService.findAllByCategory(brandName);
            model.addAttribute("brandName", brandName);
            model.addAttribute("products", products);
        }
        model.addAttribute("productsSuggest", randomProducts);
        return "another-temple/all_product";
    }

    @GetMapping("/allProduct")
    public String showAllProductsWithoutBrand(Model model) {
        List<Product> result = productService.findAllProducts();
        Collections.shuffle(result);
        List<Product> randomProducts = result.stream()
                .limit(20)
                .toList();
        model.addAttribute("products", randomProducts);
        model.addAttribute("brandName", "Bán Chạy");
        return "another-temple/all_product";
    }

    @GetMapping("/search")
    public String getSearchProductPage(@RequestParam("searchKey") String searchKey, Model model) {
        List<Product> product = productService.findProductByKey(searchKey);
        model.addAttribute("products", product);
        System.out.println(product);
        return "another-temple/search_result";
    }

    @GetMapping("/searchProduct")
    public String getSearchProduct(
            @Param("brand") String brand,
            @Param("type") String type,
            @Param("rangePrice") String rangePrice,
            Model model) {
        Double minPrice = null;
        Double maxPrice = null;
        if (rangePrice != null && !rangePrice.isEmpty()) {
            String[] rangeParts = rangePrice.split("&");
            List<Double> allPrices = new ArrayList<>();

            for (String part : rangeParts) {
                if (part.contains(",")) {
                    String[] prices = part.split(",");
                    for (String price : prices) {
                        allPrices.add(Double.valueOf(price));
                    }
                } else {
                    allPrices.add(Double.valueOf(part));
                }
            }
            if (!allPrices.isEmpty()) {
                minPrice = Collections.min(allPrices);
                maxPrice = Collections.max(allPrices);
            }
        }

        List<Product> product = productService.findAllUser(brand, type, minPrice, maxPrice);
        model.addAttribute("products", product);
        model.addAttribute("brandName", brand);

        return "another-temple/search_result";
    }


    @GetMapping("/bestSeller")
    public String showBestSeller(Model model, @Param("brand") String brand) {
        if (brand.equals("all")) {
            List<Product> products = productService.findAllProducts();
            Collections.shuffle(products);
            List<Product> randomProducts = products.stream()
                    .limit(20)
                    .toList();
            model.addAttribute("products", randomProducts);
            model.addAttribute("brandName", brand);
            return "another-temple/search_result";
        }
        List<Product> products = productService.findAllByCategory(brand);
        Collections.shuffle(products);
        List<Product> randomProducts = products.stream()
                .limit(20)
                .toList();
        model.addAttribute("products", randomProducts);
        model.addAttribute("brandName", brand);
        return "another-temple/search_result";
    }

    @GetMapping("/saleAsc")
    public String showProductBySalePrice(Model model, @Param("brand") String brand) {
        if (!brand.equals("all")) {
            List<Product> products = productService.sortProductsBySalePriceDesc(brand);
            model.addAttribute("products", products);
            model.addAttribute("brandName", brand);
            return "another-temple/search_result";
        }
        List<Product> products = productService.findAllByOrderBySalePriceAsc();
        model.addAttribute("products", products);
        model.addAttribute("brandName", brand);

        return "another-temple/search_result";
    }

    @GetMapping("/priceASC")
    public String showProductBySalePriceAsc(Model model, @Param("brand") String brand) {
        if (!brand.equals("all")) {
            List<Product> products = productService.sortProductsByCategoryASC(brand);
            model.addAttribute("products", products);
            model.addAttribute("brandName", brand);

            return "another-temple/search_result";
        }
        List<Product> products = productService.findAllByOrderByOriginalPriceAsc();
        model.addAttribute("products", products);
        model.addAttribute("brandName", brand);

        return "another-temple/search_result";
    }

    @GetMapping("/priceDESC")
    public String showProductByPriceDesc(Model model, @Param("brand") String brand) {
        if (!brand.equals("all")) {
            List<Product> products = productService.sortProductsByCategoryDESC(brand);
            model.addAttribute("products", products);
            model.addAttribute("brandName", brand);

            return "another-temple/search_result";
        }
        List<Product> products = productService.findAllByOrderByOriginalPriceDesc();
        model.addAttribute("products", products);
        model.addAttribute("brandName", brand);
        return "another-temple/search_result";
    }
}
