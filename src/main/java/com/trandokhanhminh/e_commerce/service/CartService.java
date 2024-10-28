package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Cart;
import com.trandokhanhminh.e_commerce.entity.Product;
import com.trandokhanhminh.e_commerce.entity.User;

public interface CartService {
    Cart addToCart(Product product, int quantity, User user);
    Cart updateCart(User user, Product product, int quantity);
    Cart deleteCartItem(User user, Product product);
}
