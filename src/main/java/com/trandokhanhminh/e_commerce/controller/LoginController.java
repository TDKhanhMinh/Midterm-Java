package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.reponsitory.RoleRepo;
import com.trandokhanhminh.e_commerce.reponsitory.UserRepo;
import com.trandokhanhminh.e_commerce.service.UserService;
import com.trandokhanhminh.e_commerce.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @Autowired
    private final UserService customerService;
    @Autowired
    private final UserServiceImpl userService;


    @Autowired
    public LoginController(UserService theCustomerService, RoleRepo roleRepo, UserServiceImpl userService, UserRepo customersRepo, BCryptPasswordEncoder encoder) {
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
        return "user-temple/login";
    }

    @GetMapping("/showRegister")
    public String showRegister(Model model) {
        User customer = new User();
        model.addAttribute("customer", customer);
        return "user-temple/registration-form";
    }

    @PostMapping("/checkRegisterCustomer")
    public String saveCustomer(@Valid @ModelAttribute("customer") User theCustomer, BindingResult result, Model theModel) {
        if (result.hasErrors()) {
            theModel.addAttribute("customer", theCustomer);
            return "user-temple/registration-form";
        }
        String userName = theCustomer.getEmail();
        User customer = customerService.findCustomerByEmail(userName);
        if (customer != null) {
            theModel.addAttribute("customer", theCustomer);
            theModel.addAttribute("error", "Email has already been registered");
            return "user-temple/registration-form";
        }
        if (theCustomer.getPassword().equals(theCustomer.getConfirmPassword())) {
            userService.saveCustomer(theCustomer);
            theModel.addAttribute("success", "Successfully registered");
            return "user-temple/login";
        } else {
            theModel.addAttribute("customer", theCustomer);
            theModel.addAttribute("error", "Password is not same");
            return "user-temple/registration-form";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        model.addAttribute("success", "Log out successfully");
        session.removeAttribute("USERNAME");
        System.out.println("User logged out");
        return "user-temple/login";
    }
}


