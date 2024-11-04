package com.trandokhanhminh.e_commerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    public int customerId;

    @NotNull(message = "Please fill in this field")
    @Size(min = 1, message = "Please fill in this field")
    @Column(name = "first_name")
    public String firstName;

    @NotNull(message = "Please fill in this field")
    @Size(min = 1, message = "Please fill in this field")
    @Column(name = "last_name")
    public String lastName;

    @NotNull(message = "Please fill in this field")
    @Size(min = 6, message = "Password must be more than 6 digit")
    @Column(name = "password")
    public String password;

    @NotNull(message = "Please fill in this field")
    @Size(min = 6, message = "Password must be more than 6 digit")
    public String confirmPassword;

    @NotNull(message = "Please fill in this field")
    @Size(min = 1, message = "Please fill in this field")
    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "gender")
    public String gender;

    @Column(name = "phone")
    public String phone;

    @Column(name = "birthday")
    public Date birthday;

    @Column(name = "province")
    public String province;

    @Column(name = "district")
    public String district;

    @Column(name = "ward")
    public String ward;

    @Column(name = "image")
    public String imageUrl;

    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

    @Setter
    @Getter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
    @Setter
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;


    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", ward='" + ward + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

