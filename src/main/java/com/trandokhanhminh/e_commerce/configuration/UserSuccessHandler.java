package com.trandokhanhminh.e_commerce.configuration;

import com.trandokhanhminh.e_commerce.entity.User;
import com.trandokhanhminh.e_commerce.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class UserSuccessHandler implements AuthenticationSuccessHandler, LogoutSuccessHandler {

    private final UserService userService;

    public UserSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("In customAuthenticationSuccessHandler");
        String userName = authentication.getName();
        System.out.println("userName=" + userName);

        User theUser = userService.findCustomerByEmail(userName);
        HttpSession session = request.getSession();
        session.setAttribute("USERNAME", theUser.getFirstName());
        session.setAttribute("id", theUser.getCustomerId());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/admin/admin");
                return;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect(request.getContextPath() + "/home-page");
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/login?error=true");
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    response.sendRedirect(request.getContextPath() + "/home-page");
                    return;
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    response.sendRedirect(request.getContextPath() + "/home-page");
                    return;
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/login?logout=true");
    }

}
