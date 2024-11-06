package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.Cart;
import com.trandokhanhminh.e_commerce.entity.Product;
import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.service.CartService;
import com.trandokhanhminh.e_commerce.service.ProductService;
import com.trandokhanhminh.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String getCart(Model model, Principal principal) {
        User user = userService.findCustomerByEmail(principal.getName());
        List<Product> products = productService.findAllProducts();
        Collections.shuffle(products);
        List<Product> randomProducts = products.stream()
                .limit(20)
                .toList();
        System.out.println(user);
        Cart cart = user.getCart();
        if (cart == null) {
            model.addAttribute("message", "No item in your cart");
        }
        model.addAttribute("cart", cart);
        model.addAttribute("products", randomProducts);
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(Model model, Principal principal,
                            @RequestParam("productId") int productId,
                            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) {
        Product product = productService.findProductByProductId(productId);
        User user = userService.findCustomerByEmail(principal.getName());
        Cart cart = cartService.addToCart(product, quantity, user);
        model.addAttribute("cart", cart);
        return "redirect:/home-page";
    }

    @PostMapping("/addToCartInSide")
    public String addToCartInSide(Model model, RedirectAttributes redirectAttributes,
                                  Principal principal, @RequestParam("productId") int productId,
                                  @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) {
        Product product = productService.findProductByProductId(productId);
        User user = userService.findCustomerByEmail(principal.getName());
        Cart cart = cartService.addToCart(product, quantity, user);
        model.addAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("success", "Thêm vào giỏ hàng thành công");
        return "redirect:/productDetails?productId=" + productId;
    }

    @PostMapping("/addToCartNow")
    public String addToCartNow(Model model, Principal principal,
                               @RequestParam("productId") int productId,
                               @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) {
        Product product = productService.findProductByProductId(productId);
        User user = userService.findCustomerByEmail(principal.getName());
        Cart cart = cartService.addToCart(product, quantity, user);
        model.addAttribute("cart", cart);
        return "redirect:/productDetails?productId=" + productId;
    }

    @PostMapping("/buyNow")
    public String buyNow(Model model, Principal principal,
                         @RequestParam("productId") int productId,
                         @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) {
        Product product = productService.findProductByProductId(productId);
        User user = userService.findCustomerByEmail(principal.getName());
        Cart cart = cartService.addToCart(product, quantity, user);
        model.addAttribute("cart", cart);
        return "redirect:/checkout";
    }

    @PostMapping("/updateCart")
    public String updateCart(Principal principal,
                             @RequestParam("productId") int productId, Model model,
                             @RequestParam(value = "quantity") int quantity) {
        User user = userService.findCustomerByEmail(principal.getName());
        Product product = productService.findProductByProductId(productId);
        Cart cart = cartService.updateCart(user, product, quantity);
        model.addAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/deleteCart")
    public String deleteCart(Principal principal, @RequestParam("itemId") int productId, Model model) {
        User user = userService.findCustomerByEmail(principal.getName());
        Product product = productService.findProductByProductId(productId);
        Cart cart = cartService.deleteCartItem(user, product);
        model.addAttribute("cart", cart);
        return "redirect:/cart";
    }


}
