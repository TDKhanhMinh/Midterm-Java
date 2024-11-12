package com.trandokhanhminh.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int id;

    @Column(name = "total_items")
    public int total_items;

    @Column(name = "total_price")
    public double total_price;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @JsonIgnore
    private User user;
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "cart")
    private Set<CartItems> cartItems;


    public Cart(double total_price, int total_items) {
        this.total_price = total_price;
        this.total_items = total_items;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", total_items=" + total_items +
                ", total_price=" + total_price +
                '}';
    }
}
