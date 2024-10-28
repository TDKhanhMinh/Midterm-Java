package com.trandokhanhminh.e_commerce.dao;

import com.trandokhanhminh.e_commerce.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();
    User findCustomerById(int id);
    User saveCustomer(User customer);
    User findUserByUserName(String userName);
    void deleteCustomerById(int id);
}
