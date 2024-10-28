package com.trandokhanhminh.e_commerce.dao;

import com.trandokhanhminh.e_commerce.entity.Role;

public interface RoleDAO {
    Role findRoleByName(String roleName);
}
