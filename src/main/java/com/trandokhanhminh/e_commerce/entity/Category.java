package com.trandokhanhminh.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category", uniqueConstraints
        = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    public int categoryId;

    @Setter
    @Getter
    @Column(name = "name")
    public String name;

    @Column (name = "is_active")
    public boolean is_active;

    @Column(name = "is_delete")
    public boolean is_deleted;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "category")
    @JsonIgnore
    private List<Product> products;

    public Category(String name) {
        this.name = name;
        this.is_active = true;
        this.is_deleted = false;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_Id=" + categoryId +
                ", name='" + name + '\'' +
                ", is_active=" + is_active +
                ", is_deleted=" + is_deleted +
                '}';
    }
}

