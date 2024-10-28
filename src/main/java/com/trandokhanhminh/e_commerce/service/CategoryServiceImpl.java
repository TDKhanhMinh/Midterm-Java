package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.entity.Category;
import com.trandokhanhminh.e_commerce.reponsitory.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public List<Category> findALL() {
        return categoryRepo.findAll();
    }

    @Override
    public Category save(Category category) {
        try {
            Category thisCategory = new Category(category.getName());
            return categoryRepo.save(thisCategory);
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public void update(Category category) {
        Category thisCategory = new Category();
        thisCategory.setCategoryId(category.getCategoryId());
        thisCategory.setName(category.getName());
        thisCategory.set_deleted(category.is_deleted());
        thisCategory.set_active(category.is_active());
        categoryRepo.save(thisCategory);
    }

    @Override
    public Category findById(int id) {
        return categoryRepo.findByCategoryId(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public void delete(Category category) {

    }

    @Override
    public void deleteById(int id) {
        Category thisCategory = categoryRepo.findByCategoryId(id);
        categoryRepo.delete(thisCategory);
    }

    @Override
    public void disableById(int id) {
        Category category = categoryRepo.findByCategoryId(id);
        category.set_active(false);
        category.set_deleted(true);
        categoryRepo.save(category);
    }

    @Override
    public void enableById(int id) {
        Category category = categoryRepo.findByCategoryId(id);
        category.set_active(true);
        category.set_deleted(false);
        categoryRepo.save(category);

    }

    @Override
    public void saveCategory(Category category) {
        categoryRepo.save(category);
    }
}
