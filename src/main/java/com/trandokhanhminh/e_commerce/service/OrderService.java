package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Cart;
import com.trandokhanhminh.e_commerce.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    void saveOrder(Cart cart, String location);

    void updateOrder(int id);

    void deleteOrder(int orderId);

    void setOrderStatusTransport(int orderId);

    void setOrderStatusCanceled(int orderId);

    void setOrderStatusDelivered(int orderId);

    Order findOrderById(int id);

    Page<Order> pageOrder(int pageNo);

    Page<Order> pageOrderByStatus(int pageNo, String status);
}
