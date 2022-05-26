package com.gorhem.citizenrestapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CitizenResponseDto {

    private Long id;

    private String name;

    private Boolean isCitizen;

    private Boolean hasDrivingLicense;

    private Map<Long, String> children;

    private Integer childrenCount;
}
