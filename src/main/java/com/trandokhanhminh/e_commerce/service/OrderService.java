package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Cart;
import com.trandokhanhminh.e_commerce.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    void saveOrder(Cart cart, String location);

    void deleteOrder(int orderId);

    Page<Order> pageOrder(int pageNo);
}
