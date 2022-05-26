package com.gorhem.citizenrestapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CitizenRequestDto {

    private Long id;

    private String name;

    private Boolean isCitizen;

    private Boolean hasDrivingLicense;

    private Set<Long> childrenIds;
}
