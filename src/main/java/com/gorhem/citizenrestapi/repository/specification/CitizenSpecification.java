package com.gorhem.citizenrestapi.repository.specification;

import com.gorhem.citizenrestapi.entity.Citizen;
import com.gorhem.citizenrestapi.model.FilterDto;
import com.gorhem.citizenrestapi.model.Operator;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class CitizenSpecification {

    public static Specification<Citizen> getSpecificationFromFilters(List<FilterDto> filter){
        FilterDto defaultFilterDto = new FilterDto();
        defaultFilterDto.setField("id");
        defaultFilterDto.setOperator(Operator.GREATER_THAN);
        defaultFilterDto.setValue("0");
        Specification<Citizen> specification =
                where(createSpecification(defaultFilterDto));
        for (FilterDto input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    public static Specification<Citizen> createSpecification(FilterDto input) {
        switch (input.getOperator()){

            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(),
                                        input.getValue()));

            case NOT_EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(),
                                        input.getValue()));

            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()),
                                (Number) castToRequiredType(
                                        root.get(input.getField()).getJavaType(),
                                        input.getValue()));

            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()),
                                (Number) castToRequiredType(
                                        root.get(input.getField()).getJavaType(),
                                        input.getValue()));

            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(input.getField())),
                                "%"+input.getValue().toLowerCase()+"%");

            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField()))
                                .value(castToRequiredType(
                                        root.get(input.getField()).getJavaType(),
                                        input.getValues()));

            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private static Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(value);
        } else if(fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        } else if(fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if(Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    private static Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }
}
