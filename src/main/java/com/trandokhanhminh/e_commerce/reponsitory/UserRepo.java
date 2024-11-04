package com.trandokhanhminh.e_commerce.reponsitory;

import com.trandokhanhminh.e_commerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("select u from User u")
    Page<User> userPage(Pageable pageable);

    User findCustomerByCustomerId(int customerId);

    //boolean findCustomerByEmail(String username);
    User findCustomerByEmail(String email);

    User findCustomerByEmailAndPassword(String username, String password);

}

