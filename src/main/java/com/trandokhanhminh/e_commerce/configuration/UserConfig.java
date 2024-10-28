package com.trandokhanhminh.e_commerce.configuration;

import com.trandokhanhminh.e_commerce.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationSuccessHandler successHandler) throws Exception {
        httpSecurity.authorizeHttpRequests(config -> config
                        //.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/").hasRole("USER")
                        .requestMatchers("/admin/**", "/").hasRole("ADMIN")
                        .requestMatchers("/showRegister").permitAll()
                        .requestMatchers("/showLogin").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/showLogin")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutUrl("/admin/logout")
                        .logoutSuccessHandler((LogoutSuccessHandler) successHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(exeption -> exeption.accessDeniedPage("/access-denied"));
        return httpSecurity.build();
    }


}
