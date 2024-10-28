package com.trandokhanhminh.e_commerce.reponsitory;

import com.trandokhanhminh.e_commerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByName(String name);
    Role findById(int id);

}
