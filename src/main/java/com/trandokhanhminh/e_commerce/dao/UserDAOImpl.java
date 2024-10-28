package com.trandokhanhminh.e_commerce.dao;

import com.trandokhanhminh.e_commerce.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private final EntityManager entityManager;
    private UserDAO userDAO;

    @Autowired
    public UserDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> theQuery = entityManager.createQuery("from User", User.class);
        return theQuery.getResultList();
    }

    @Override
    public User findCustomerById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public User saveCustomer(User customer) {
        return entityManager.merge(customer);
    }

    @Override
    public User findUserByUserName(String userName) {
        TypedQuery<User> theQuery = entityManager.createQuery("from User where email=:uName ", User.class);
        theQuery.setParameter("uName", userName);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception ignored) {
        }

        return theUser;
    }

    @Override
    public void deleteCustomerById(int id) {
        User theCustomer = entityManager.find(User.class, id);
        entityManager.remove(theCustomer);
    }
}
