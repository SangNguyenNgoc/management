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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    private Set<Order> orders;
}
