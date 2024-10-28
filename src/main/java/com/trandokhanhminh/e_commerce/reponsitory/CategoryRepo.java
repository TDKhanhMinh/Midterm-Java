package com.trandokhanhminh.e_commerce.reponsitory;

import com.trandokhanhminh.e_commerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findByCategoryId(int categoryId);
    Category findByName(String name);
}
