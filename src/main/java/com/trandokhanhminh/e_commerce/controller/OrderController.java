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
import java.util.ArrayList;
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
    public String placeOrder(Principal principal, Model model,
                             @RequestParam("orderType") String orderType) {
        User user = userService.findCustomerByEmail(principal.getName());
        List<Order> orderList = user.getOrders();
        switch (orderType) {
            case "all" -> {
                model.addAttribute("user", user);
                model.addAttribute("orderList", orderList);
                model.addAttribute("status", "all");
                return "another-temple/place_order";
            }
            case "wait" -> {
                List<Order> waiting = new ArrayList<>();
                for (Order order : orderList) {

                    if (order.getStatus().equals("Đang chờ duyệt")) {
                        waiting.add(order);
                    }
                }
                model.addAttribute("user", user);
                model.addAttribute("orderList", waiting);
                model.addAttribute("status", "wait");
                return "another-temple/place_order";
            }
            case "prepare" -> {
                List<Order> prepare = new ArrayList<>();
                for (Order order : orderList) {
                    if (order.getStatus().equals("Đang chờ đơn vị vận chuyển")) {
                        prepare.add(order);
                    }
                }
                model.addAttribute("user", user);
                model.addAttribute("status", "prepare");
                model.addAttribute("orderList", prepare);
                return "another-temple/place_order";
            }
            case "transport" -> {
                List<Order> transport = new ArrayList<>();
                for (Order order : orderList) {
                    if (order.getStatus().equals("Đang giao")) {
                        transport.add(order);
                    }
                }
                model.addAttribute("user", user);
                model.addAttribute("orderList", transport);
                model.addAttribute("status", "transport");

                return "another-temple/place_order";
            }
            case "delivered" -> {
                List<Order> delivered = new ArrayList<>();
                for (Order order : orderList) {
                    if (order.getStatus().equals("Đã giao")) {
                        delivered.add(order);
                    }
                }
                model.addAttribute("user", user);
                model.addAttribute("status", "delivered");

                model.addAttribute("orderList", delivered);
                return "another-temple/place_order";
            }
            default -> {
                List<Order> canceled = new ArrayList<>();
                for (Order order : orderList) {
                    if (order.getStatus().equals("Đã hủy")) {
                        canceled.add(order);
                    }
                }
                model.addAttribute("user", user);
                model.addAttribute("status", "canceled");

                model.addAttribute("orderList", canceled);
                return "another-temple/place_order";
            }
        }

    }

    @PostMapping("/saveOrder")
    public String saveOrder(Principal principal, RedirectAttributes redirectAttributes,
                            @Param("city") String city,
                            @Param("district") String district,
                            @Param("ward") String ward,
                            @Param("address") String address,
                            @Param("phone") String phone) {
        if (address.isEmpty() || city.isEmpty() || district.isEmpty() || ward.isEmpty() || phone.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng nhập địa chỉ cụ thể");
            return "redirect:/checkout";
        } else {
            User user = userService.findCustomerByEmail(principal.getName());
            userService.updateUserPhone(phone, user);
            Cart cart = user.getCart();
            String location = address + ", " + ward + ", " + district + ", " + city;
            orderService.saveOrder(cart, location);
            String orderType = "all";
            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công");
            return "redirect:/placeOrder?orderType=" + orderType;

        }
    }

    @GetMapping("/orderInfo")
    public String getOrderInfo(@RequestParam("orderId") int oderId,
                               Principal principal, Model model) {
        User user = userService.findCustomerByEmail(principal.getName());
        List<Order> orderList = user.getOrders();
        for (Order order : orderList) {
            if (order.getOder_id() == oderId) {
                model.addAttribute("user", user);
                model.addAttribute("order", order);
                return "another-temple/order_information";
            }
        }
        return "another-temple/order_information";
    }

    @GetMapping("/canceledOrder")
    public String deleteOrder(@RequestParam("orderId") int orderId, RedirectAttributes redirectAttributes) {
        String orderType = "all";
        orderService.setOrderStatusCanceled(orderId);
        redirectAttributes.addFlashAttribute("success", "Hủy đơn hàng thành công");
        return "redirect:/placeOrder?orderType=" + orderType;
    }
}
