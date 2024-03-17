package com.example.markethibernate.dal.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailId implements Serializable {

    private Order order;
    private Vegetable vegetable;
}
