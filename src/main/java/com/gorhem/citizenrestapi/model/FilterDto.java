package com.gorhem.citizenrestapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterDto {
    private String field;
    private Operator operator;
    private String value;
    private List<String> values;
}
