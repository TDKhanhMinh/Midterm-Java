package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean checkLogin(String email, String password);

    List<User> findAll();

    User findCustomerById(int id);

    void saveCustomer(User customer);

    void deleteCustomerById(int id);

    User findCustomerByEmail(String email);

    Page<User> userPage(int pageNo);

    void updateCustomer(User customer);
}

