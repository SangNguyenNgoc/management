package com.example.markethibernate.dal.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "order_details")
@IdClass(OrderDetailId.class)
public class OrderDetail {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vegetable_id")
    private Vegetable vegetable;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Float price;
}
