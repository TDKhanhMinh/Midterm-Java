package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.Product;
import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.reponsitory.RoleRepo;
import com.trandokhanhminh.e_commerce.service.ProductService;
import com.trandokhanhminh.e_commerce.service.UserService;
import com.trandokhanhminh.e_commerce.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private final UserService customerService;
    @Autowired
    private final UserServiceImpl userService;
    @Autowired
    private ProductService productService;

    @Autowired
    public LoginController(UserService theCustomerService, RoleRepo roleRepo, UserServiceImpl userService) {
        this.customerService = theCustomerService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }





    @GetMapping("/access-denied")
    public String showDenied() {
        return "access-denied";
    }

    @RequestMapping("/showLogin")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/showRegister")
    public String showRegister(Model model) {
        User customer = new User();
        model.addAttribute("customer", customer);
        return "registration-form";
    }

    @PostMapping("/checkRegisterCustomer")
    public String saveCustomer(@Valid @ModelAttribute("customer") User theCustomer, BindingResult result, Model theModel) {
        if (result.hasErrors()) {
            theModel.addAttribute("customer", theCustomer);
            return "registration-form";
        }
        String userName = theCustomer.getEmail();
        User customer = customerService.findCustomerByEmail(userName);
        if (customer != null) {
            theModel.addAttribute("customer", theCustomer);
            theModel.addAttribute("error", "Email has already been registered");
            return "registration-form";
        }
        if (theCustomer.getPassword().equals(theCustomer.getConfirmPassword())) {

            userService.saveCustomer(theCustomer);
            theModel.addAttribute("success", "Successfully registered");
            return "login";
        } else {
            theModel.addAttribute("customer", theCustomer);
            theModel.addAttribute("error", "Password is not same");
            return "registration-form";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("USERNAME");
        return "login";
    }
}


