package com.example.markethibernate.dal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "vegetables")
public class Vegetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Category category;

    @OneToMany(
            mappedBy = "vegetable",
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    private Set<OrderDetail> orderDetails;
}
