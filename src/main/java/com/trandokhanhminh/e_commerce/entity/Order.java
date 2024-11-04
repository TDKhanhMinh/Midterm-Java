package com.trandokhanhminh.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    public int oder_id;

    @Column(name = "product_quantity")
    public int product_quantity;

    @Column(name = "date")
    public Date date;

    @Column(name = "expected_date")
    public Date expected_date;


    @Column(name = "status")
    public String status;

    @Column(name = "total_price")
    public double total_price;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OderDetails> orderDetailList;


    public Order(int product_quantity, Date date, double total_price) {
        this.product_quantity = product_quantity;
        this.date = date;
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oder_id=" + oder_id +
                ", product_quantity=" + product_quantity +
                ", date=" + date +
                ", expected_date=" + expected_date +
                ", status='" + status + '\'' +
                ", total_price=" + total_price +
                ", user=" + user +
                ", orderDetailList=" + orderDetailList +
                '}';
    }
}
