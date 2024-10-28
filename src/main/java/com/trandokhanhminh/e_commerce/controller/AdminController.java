package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.Category;
import com.trandokhanhminh.e_commerce.entity.Order;
import com.trandokhanhminh.e_commerce.entity.Product;
import com.trandokhanhminh.e_commerce.reponsitory.CategoryRepo;
import com.trandokhanhminh.e_commerce.reponsitory.ProductRepo;
import com.trandokhanhminh.e_commerce.service.CategoryService;
import com.trandokhanhminh.e_commerce.service.OrderService;
import com.trandokhanhminh.e_commerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/showLogin")
    public String getShowLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("USERNAME");
        //session.invalidate();
        return "login";
    }

    // Start Controller category
    @GetMapping("/category")
    public String getCategory(Model model) {
        List<Category> categories = categoryService.findALL();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category());
        return "category";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("categoryNew") Category category, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Please enter a category name");
                return "redirect:/admin/category";
            } else if (categoryService.findByName(category.getName()) != null) {
                redirectAttributes.addFlashAttribute("error", "This category is already exists");
                return "redirect:/admin/category";
            } else {
                categoryService.save(category);
                redirectAttributes.addFlashAttribute("success", "Added successfully");
            }
        } catch (Exception e) {
            e.getMessage();
            redirectAttributes.addFlashAttribute("error", "Failed to add category");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/updateCategory")
    public String updateCategory(@RequestParam("categoryId") int categoryId, Model theModel) {
        Category category = categoryService.findById(categoryId);
        theModel.addAttribute("categoryUpdate", category);
        return "update_category";
    }


    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("categoryUpdate") Category category,
                               RedirectAttributes redirectAttributes) {
        try {
            Category categoryUpdate = categoryService.findById(category.getCategoryId());
            categoryUpdate.setName(category.getName());
            categoryRepo.save(categoryUpdate);
            redirectAttributes.addFlashAttribute("success", "Update successfully");
        } catch (Exception e) {
            e.getMessage();
            redirectAttributes.addFlashAttribute("error", "Failed to change category name");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory")
    public String deleteCategory(RedirectAttributes redirectAttributes, @RequestParam("categoryId") int categoryId) {
        categoryService.deleteById(categoryId);
        redirectAttributes.addFlashAttribute("success", "Delete successfully");
        return "redirect:/admin/category";
    }

    @GetMapping("/enableCategory")
    public String enableCategory(RedirectAttributes redirectAttributes, @RequestParam("categoryId") int categoryId) {
        categoryService.enableById(categoryId);
        redirectAttributes.addFlashAttribute("success", "Enable successfully");
        return "redirect:/admin/category";
    }

    @GetMapping("/disableCategory")
    public String disableCategory(RedirectAttributes redirectAttributes, @RequestParam("categoryId") int categoryId) {
        categoryService.disableById(categoryId);
        redirectAttributes.addFlashAttribute("success", "Disable successfully");
        return "redirect:/admin/category";
    }
//End controller category

//Start product controller

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;

//    @GetMapping("/product")
//    public String getProduct(Model model) {
//        List<Product> products = productService.findAllProducts();
//        model.addAttribute("products", products);
//        model.addAttribute("size", products.size());
//        model.addAttribute("newProduct", new Product());
//        return "product";
//    }

    @GetMapping("/product/{pageNum}")
    public String getProductPage(@PathVariable("pageNum") int pageNum, Model model) {
        Page<Product> product = productService.pageProduct(pageNum);
        model.addAttribute("size", product.getSize());
        model.addAttribute("totalPages", product.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("products", product);
        return "product";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        List<Category> listCategories = categoryRepo.findAll();
        listCategories.removeIf(Category::is_deleted);
        model.addAttribute("categories", listCategories);
        return "add_product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(RedirectAttributes redirectAttributes, @ModelAttribute("newProduct") Product product,
                              @RequestParam("imageProduct") MultipartFile imageProductFile) throws IOException {
        productService.saveProduct(imageProductFile, product);
        redirectAttributes.addFlashAttribute("success", "Added product successfully");
        return "redirect:/admin/product/0";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(RedirectAttributes redirectAttributes, @RequestParam("productId") int productId) {
        productService.removeByProductId(productId);
        redirectAttributes.addFlashAttribute("success", "Delete product successfully");
        return "redirect:/admin/product/0";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(@RequestParam("productId") int productId, Model model) {
        Product product = productRepo.findByProductId(productId);
        model.addAttribute("productUpdate", product);
        List<Category> listCategories = categoryRepo.findAll();
        listCategories.removeIf(Category::is_deleted);
        model.addAttribute("categories", listCategories);
        return "update_product";
    }

    @PostMapping("/saveUpdateProduct")
    public String saveProduct(@ModelAttribute("productUpdate") Product newProduct,
                              RedirectAttributes redirectAttributes, @RequestParam("imageProduct") MultipartFile imageProductFile) {
        try {
            Product productUpdate = productRepo.findByProductId(newProduct.getProductId());
            productService.updateProduct(imageProductFile, newProduct, productUpdate);
            redirectAttributes.addFlashAttribute("success", "Update successfully");
        } catch (Exception e) {
            e.getMessage();
            redirectAttributes.addFlashAttribute("error", "Failed to update product");
        }
        return "redirect:/admin/product/0";
    }
//Begin order controller
    @GetMapping("/order/{pageNum}")
    public String getOrder(@PathVariable("pageNum") int pageNum,Model model) {
        Page<Order> orders = orderService.pageOrder(pageNum);
        model.addAttribute("size", orders.getSize());
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("orders", orders);
        return "order_manager";
    }

    @GetMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("orderId") int orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/admin/order/0";
    }


}