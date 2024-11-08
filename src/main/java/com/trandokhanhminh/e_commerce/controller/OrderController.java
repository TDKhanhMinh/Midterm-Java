package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.Cart;
import com.trandokhanhminh.e_commerce.entity.Order;
import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.service.CartService;
import com.trandokhanhminh.e_commerce.service.OrderService;
import com.trandokhanhminh.e_commerce.service.ProductService;
import com.trandokhanhminh.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {


    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/checkout")
    public String checkout(Principal principal, Model model, @Param("address") String address) {
        User user = userService.findCustomerByEmail(principal.getName());
        Cart cart = user.getCart();
        model.addAttribute("address", address);
        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        return "another-temple/check_out";
    }

    @GetMapping("/placeOrder")
    public String placeOrder(Principal principal, Model model) {
        User user = userService.findCustomerByEmail(principal.getName());
        List<Order> orderList = user.getOrders();
        model.addAttribute("user", user);
        model.addAttribute("orderList", orderList);
        return "another-temple/place_order";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(Principal principal, RedirectAttributes redirectAttributes,
                            @Param("city") String city,
                            @Param("district") String district,
                            @Param("ward") String ward,
                            @Param("address") String address) {
        if (address.isEmpty() || city.isEmpty() || district.isEmpty() || ward.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng nhập địa chỉ cụ thể");
            return "redirect:/checkout";
        } else {
            User user = userService.findCustomerByEmail(principal.getName());
            Cart cart = user.getCart();
            String location = address + "," + ward + "," + district + "," + city;
            orderService.saveOrder(cart, location);
            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công");
            return "redirect:/placeOrder";
        }
    }

    @GetMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("orderId") int orderId, RedirectAttributes redirectAttributes) {
        orderService.deleteOrder(orderId);
        redirectAttributes.addFlashAttribute("success", "Hủy đơn hàng thành công");
        return "redirect:/placeOrder";
    }
}
