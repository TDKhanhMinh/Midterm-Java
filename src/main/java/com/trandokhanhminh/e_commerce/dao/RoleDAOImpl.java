package com.trandokhanhminh.e_commerce.dao;

import com.trandokhanhminh.e_commerce.entity.Role;
import com.trandokhanhminh.e_commerce.reponsitory.RoleRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private EntityManager entity;
    private RoleDAO roleDAO;
    private final RoleRepo roleRepo;

    public RoleDAOImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role findRoleByName(String roleName) {

        return roleRepo.findByName(roleName);
    }
}
