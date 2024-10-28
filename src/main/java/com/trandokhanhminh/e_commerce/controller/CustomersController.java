package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.reponsitory.UserRepo;
import com.trandokhanhminh.e_commerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomersController {
    @Autowired
    private final UserService customerService;
    @Autowired
    private UserRepo userRepo;


    public CustomersController(UserService theCustomerService) {
        customerService = theCustomerService;
    }


    @GetMapping(value = "/list")
    public String getList(Model theModel, HttpSession session) {
        if (session.getAttribute("USERNAME") != null) {
            List<User> theList = customerService.findAll();
            theModel.addAttribute("customers", theList);
            return "list-customers";
        } else return "login";
    }


    @GetMapping("/addForm")
    public String addForm(Model theModel) {
        User theCustomer = new User();
        theModel.addAttribute("customer", theCustomer);
        return "registration-form";
    }


    @GetMapping(value = "/profile")
    public String getProfile(@RequestParam("customerId") int customerId, Model model) {
        User customer = customerService.findCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "profile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@Valid @ModelAttribute("customerUpdate") User customer, @RequestParam("customerId") int customerID, BindingResult result, Model theModel) {
        //System.out.println(customer);
        theModel.addAttribute("customerId", customerID);
        User user = userRepo.findCustomerByCustomerId(customerID);
        System.out.println(user);
        if (result.hasErrors()) {
            theModel.addAttribute("customer", customer);
            result.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            return "update-profile";
        } else {
            user.setFirstName(customer.getFirstName());

            user.setLastName(customer.getLastName());

            user.setEmail(customer.getEmail());

            user.setBirthday(customer.getBirthday());

            user.setDistrict(customer.getDistrict());

            user.setGender(customer.getGender());

            user.setPhone(customer.getPhone());

            user.setProvince(customer.getProvince());

            user.setWard(customer.getWard());

            System.out.println(user);
            userRepo.save(user);
            return "redirect:/profile?customerId=" + customerID;
        }
    }

    @GetMapping(value = "/update")
    public String update(@RequestParam("customerId") int theCustomerId, Model theModel) {
        User theCustomer = userRepo.findCustomerByCustomerId(theCustomerId);
        theModel.addAttribute("customerUpdate", theCustomer);
        return "update-profile";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("customerId") int theId) {
        customerService.deleteCustomerById(theId);
        return "redirect:/list";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") User theCustomer, BindingResult result, Model theModel) {
        if (result.hasErrors()) {
            theModel.addAttribute("customer", theCustomer);
            return "registration-form";
        } else {
            System.out.println(theCustomer.getCustomerId());
            customerService.updateCustomer(theCustomer);
            return "redirect:/list";
        }
    }


}


