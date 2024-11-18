package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Cart;
import com.trandokhanhminh.e_commerce.entity.CartItems;
import com.trandokhanhminh.e_commerce.entity.OderDetails;
import com.trandokhanhminh.e_commerce.entity.Order;
import com.trandokhanhminh.e_commerce.reponsitory.CartItemsRepo;
import com.trandokhanhminh.e_commerce.reponsitory.CartRepo;
import com.trandokhanhminh.e_commerce.reponsitory.OrderDetailsRepo;
import com.trandokhanhminh.e_commerce.reponsitory.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Override
    public void saveOrder(Cart cart, String location) {
        Order order = new Order();
        order.setStatus("Đang chờ duyệt");
        order.setDate(new java.sql.Date(System.currentTimeMillis()));
        Random random = new Random();
        int date = random.nextInt(4) + 2;
        order.setExpected_date(new java.sql.Date(System.currentTimeMillis() + date * 24 * 60 * 60 * 1000L));
        order.setUser(cart.getUser());
        order.setProduct_quantity(cart.getTotal_items());
        order.setTotal_price(cart.getTotal_price());
        order.setAddress(location);
        List<OderDetails> orderDetailsList = new ArrayList<>();
        for (CartItems cartItem : cart.getCartItems()) {
            OderDetails oderDetails = new OderDetails();
            oderDetails.setOrder(order);
            oderDetails.setQuantity(cartItem.getQuantity());
            oderDetails.setProduct(cartItem.getProduct());
            long totalPrice = cartItem.getProduct().getOriginalPrice() - cartItem.getProduct().getOriginalPrice() * cartItem.getProduct().getSalePrice() / 100;
            oderDetails.setUnitPrice(totalPrice);
            orderDetailsRepo.save(oderDetails);
            orderDetailsList.add(oderDetails);
            cartItemsRepo.delete(cartItem);
        }

        order.setOrderDetailList(orderDetailsList);
        cart.setCartItems(new HashSet<>());
        cart.setTotal_items(0);
        cart.setTotal_price(0);
        cartRepo.save(cart);
        orderRepo.save(order);
    }

    @Override
    public void updateOrder(int id) {
        Order oldOrder = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        oldOrder.setStatus("Đang chờ đơn vị vận chuyển");
        orderRepo.save(oldOrder);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepo.deleteById(orderId);
    }

    @Override
    public Page<Order> pageOrder(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        return orderRepo.orderPage(pageable);
    }
}
