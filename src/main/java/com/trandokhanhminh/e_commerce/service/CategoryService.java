package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findALL();

    Category save(Category category);

    void update(Category category);

    Category findById(int id);

    Category findByName(String name);

    void delete(Category category);

    void deleteById(int id);

    void disableById(int id);

    void enableById(int id);




}
