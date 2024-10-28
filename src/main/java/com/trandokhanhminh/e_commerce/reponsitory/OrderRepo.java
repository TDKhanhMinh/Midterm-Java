package com.trandokhanhminh.e_commerce.reponsitory;

import com.trandokhanhminh.e_commerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    @Query(" select o from  Order o ")
    Page<Order> orderPage(Pageable pageable);}
