package com.gorhem.citizenrestapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private Boolean isCitizen;

    private Boolean hasDrivingLicense;

    private Long parentId;

    private Integer childrenCount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="parentId")
    private Set<Citizen> children = new HashSet<Citizen>();
}
