package com.trandokhanhminh.e_commerce.reponsitory;

import com.trandokhanhminh.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {


    User findCustomerByCustomerId(int customerId);

    //boolean findCustomerByEmail(String username);
    User findCustomerByEmail(String email);

    User findCustomerByEmailAndPassword(String username, String password);

}

