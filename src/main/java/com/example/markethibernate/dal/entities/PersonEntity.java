package com.example.markethibernate.dal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "department", length = 100, nullable = false)
    private String department;

    @Column(name = "profession", length = 100, nullable = false)
    private String profession;

    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "status", nullable = false)
    private Boolean status;


    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<PenalizeEntity> penalties;

    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<UsageInfoEntity> usageInfos;

}
