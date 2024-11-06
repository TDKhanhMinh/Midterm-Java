package com.trandokhanhminh.e_commerce.configuration;

import com.trandokhanhminh.e_commerce.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class UserConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)-> web.ignoring().requestMatchers("*/img/**","*/css/**","*/js/**","*/scss/**","*/vendor/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, UserSuccessHandler userSuccessHandler) throws Exception {
        httpSecurity.authorizeHttpRequests(config -> config
                        .requestMatchers("/").hasRole("USER")
                        .requestMatchers("/admin/**", "/").hasRole("ADMIN")
                        .requestMatchers("/showRegister").permitAll()
                        .requestMatchers("/showLogin").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/showLogin")
                        .loginProcessingUrl("/login")
                        .successHandler(userSuccessHandler)
                        .failureForwardUrl("/showLogin?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessHandler(userSuccessHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(exeption -> exeption.accessDeniedPage("/access-denied"));
        return httpSecurity.build();
    }


}
