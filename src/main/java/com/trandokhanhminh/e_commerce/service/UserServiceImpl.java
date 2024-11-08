package com.trandokhanhminh.e_commerce.service;

import com.trandokhanhminh.e_commerce.dao.RoleDAO;
import com.trandokhanhminh.e_commerce.entity.Role;
import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.reponsitory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo customersRepo;
    private final BCryptPasswordEncoder encoder;
    private final RoleDAO roleDAO;

    @Autowired
    public UserServiceImpl(UserRepo theCustomersRepo, BCryptPasswordEncoder encoder, RoleDAO roleDAO) {
        customersRepo = theCustomersRepo;
        this.encoder = encoder;
        this.roleDAO = roleDAO;
    }

    @Override
    public boolean checkLogin(String email, String password) {
        User customer = customersRepo.findCustomerByEmail(email);
        return customer != null && customer.getPassword().equals(password);
    }


    @Override
    public List<User> findAll() {
        return customersRepo.findAll();
    }

    @Override
    public User findCustomerById(int id) {
        User result = customersRepo.findCustomerByCustomerId(id);
        User customer;
        if (result != null) {
            customer = result;
        } else {
            throw new RuntimeException();
        }
        return customer;
    }


    @Override
    public void saveCustomer(User customer) {
        User user = new User();
        user.setFirstName(customer.getFirstName());
        user.setLastName(customer.getLastName());
        user.setEmail(customer.getEmail());
        user.setPassword(encoder.encode(customer.getPassword()));
        user.setConfirmPassword(customer.getConfirmPassword());
        user.setRoles(Collections.singletonList(roleDAO.findRoleByName("ROLE_USER")));
        customersRepo.save(user);
    }

    @Override
    public void deleteCustomerById(int id) {
        customersRepo.deleteById(id);
    }

    @Override
    public User findCustomerByEmail(String email) {
        return customersRepo.findCustomerByEmail(email);
    }

    @Override
    public Page<User> userPage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        return customersRepo.userPage(pageable);
    }

    @Override
    public void updateCustomer(User customer) {
        User user = customersRepo.findCustomerByCustomerId(customer.getCustomerId());

        user.setFirstName(customer.getFirstName());

        user.setLastName(customer.getLastName());

        user.setEmail(customer.getEmail());

        user.setPassword(encoder.encode(customer.getPassword()));

        user.setConfirmPassword(customer.getConfirmPassword());

        user.setRoles(Collections.singletonList(roleDAO.findRoleByName("ROLE_USER")));

        user.setBirthday(customer.getBirthday());

        user.setDistrict(customer.getDistrict());

        user.setGender(customer.getGender());

        user.setPhone(customer.getPhone());

        user.setProvince(customer.getProvince());

        user.setWard(customer.getWard());

        customersRepo.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = customersRepo.findCustomerByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }
        return authorities;
    }
}
