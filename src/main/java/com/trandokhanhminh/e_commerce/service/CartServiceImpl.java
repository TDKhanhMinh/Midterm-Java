package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Cart;
import com.trandokhanhminh.e_commerce.entity.CartItems;
import com.trandokhanhminh.e_commerce.entity.Product;
import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.reponsitory.CartItemsRepo;
import com.trandokhanhminh.e_commerce.reponsitory.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public Cart addToCart(Product product, int quantity, User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCartItems(new HashSet<>());
        }
        Set<CartItems> cartItems = cart.getCartItems();
        CartItems newcartItems = findCartItems(cartItems, product.getProductId());

        if (newcartItems == null) {
            newcartItems = new CartItems();
            newcartItems.setProduct(product);
            double cost = product.getOriginalPrice() - (double) (product.getOriginalPrice() * product.getSalePrice()) / 100;
            newcartItems.setTotalPrice(quantity * cost);
            newcartItems.setQuantity(quantity);
            newcartItems.setCart(cart);
            cartItems.add(newcartItems);
            cartItemsRepo.save(newcartItems);
        } else {
                double cost = product.getOriginalPrice() - (double) (product.getOriginalPrice() * product.getSalePrice()) / 100;
                newcartItems.setQuantity(newcartItems.getQuantity() + quantity);
                newcartItems.setTotalPrice(newcartItems.getTotalPrice() + quantity * cost);
                cartItemsRepo.save(newcartItems);
        }

        int totalItems = totalItems(cart.getCartItems());
        double totalPrice = totalPrice(cart.getCartItems());

        cart.setCartItems(cartItems);
        cart.setTotal_price(totalPrice);
        cart.setTotal_items(totalItems);
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    @Override
    public Cart updateCart(User user, Product product, int quantity) {
        Cart cart = user.getCart();
        Set<CartItems> cartItems = cart.getCartItems();
        CartItems newCartItems = findCartItems(cartItems, product.getProductId());
        newCartItems.setQuantity(quantity);
        newCartItems.setTotalPrice(quantity * (product.getOriginalPrice() - (double) (product.getOriginalPrice() * product.getSalePrice()) / 100));
        cartItemsRepo.save(newCartItems);

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        cart.setTotal_price(totalPrice);
        cart.setTotal_items(totalItems);
        cartRepo.save(cart);
        return cart;
    }

    @Override
    public Cart deleteCartItem(User user, Product product) {
        Cart cart = user.getCart();
        Set<CartItems> cartItems = cart.getCartItems();
        CartItems newCartItems = findCartItems(cartItems,product.productId);
        cartItems.remove(newCartItems);
        cartItemsRepo.delete(newCartItems);


        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        cart.setCartItems(cartItems);
        cart.setTotal_price(totalPrice);
        cart.setTotal_items(totalItems);
        return cartRepo.save(cart);
    }

    private CartItems findCartItems(Set<CartItems> cartItems, int productId) {

        if (cartItems == null) {
            return null;
        }
        CartItems newCartItems = null;
        for (CartItems items : cartItems) {
            if (items.getProduct().getProductId() == productId) {
                newCartItems = items;
            }
        }
        return newCartItems;
    }

    private int totalItems(Set<CartItems> cart) {
        int total = 0;
        for (CartItems items : cart) {
            total += items.getQuantity();
        }
        return total;
    }

    private double totalPrice(Set<CartItems> cart) {
        double total = 0;
        for (CartItems items : cart) {
            total += items.getTotalPrice();
        }
        return total;
    }
}
