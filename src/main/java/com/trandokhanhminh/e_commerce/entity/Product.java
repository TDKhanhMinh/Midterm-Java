package com.trandokhanhminh.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    public int productId;

    @Column(name = "name")
    public String name;

    @Column(name = "original_price")
    public long originalPrice;

    @Column(name = "type")
    public String productType;

    @Column(name = "sale_price")
    public long salePrice;

    @Column(name = "quantity")
    public int quantity;

    @Setter
    @Getter
    @Column(name = "image",columnDefinition = "MEDIUMBLOB")
    public String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", originalPrice=" + originalPrice +
                ", productType='" + productType + '\'' +
                ", salePrice=" + salePrice +
                ", quantity=" + quantity +
                ", imageUrl='" + imageUrl + '\'' +
                ", category=" + category +
                '}';
    }
}

