package com.trandokhanhminh.e_commerce.reponsitory;

import com.trandokhanhminh.e_commerce.entity.OderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OderDetails,Integer> {
}
