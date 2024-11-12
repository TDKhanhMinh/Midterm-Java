package com.trandokhanhminh.e_commerce.controller;

import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.reponsitory.UserRepo;
import com.trandokhanhminh.e_commerce.service.ProductService;
import com.trandokhanhminh.e_commerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CustomersController {
    @Autowired
    private final UserService customerService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductService productService;

    @Autowired
    private final BCryptPasswordEncoder encoder;


    public CustomersController(UserService theCustomerService, BCryptPasswordEncoder encoder) {
        customerService = theCustomerService;
        this.encoder = encoder;
    }


    @GetMapping("/addForm")
    public String addForm(Model theModel) {
        User theCustomer = new User();
        theModel.addAttribute("customer", theCustomer);
        return "user-temple/registration-form";
    }


    @GetMapping(value = "/profile")
    public String getProfile(@RequestParam("customerId") int customerId, Model model) {
        User customer = customerService.findCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "user-temple/profile";
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
            return "user-temple/update-profile";
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
        return "user-temple/update-profile";
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
            return "user-temple/registration-form";
        } else {
            customerService.updateCustomer(theCustomer);
            return "redirect:/list";
        }
    }


    @GetMapping("/forgotPassword")
    public String getForgotPassword() {
        return "user-temple/forgot-password";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model, Principal principal) {
        User user = customerService.findCustomerByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user-temple/change-password";
    }

    @PostMapping("/savePassword")
    public String savePassword(RedirectAttributes redirectAttributes, Principal principal, @RequestParam("new-password") String newPassword) {
        User user = customerService.findCustomerByEmail(principal.getName());
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
        redirectAttributes.addFlashAttribute("success", "Password has been changed");
        return "redirect:/profile?customerId=" + user.getCustomerId();
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@Validated RedirectAttributes redirectAttributes,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() | confirmPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "All fields are required");
            return "redirect:/forgotPassword";
        } else if (!customerService.checkForgot(email, password, confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/forgotPassword";
        } else if (customerService.findCustomerByEmail(email) == null) {
            redirectAttributes.addFlashAttribute("error", "Email does not exist");
            return "redirect:/forgotPassword";
        } else {
            User user = customerService.findCustomerByEmail(email);
            user.setPassword(encoder.encode(password));
            userRepo.save(user);
            redirectAttributes.addFlashAttribute("success", "Password reset successfully");
            return "redirect:/showLogin";
        }
    }


}


