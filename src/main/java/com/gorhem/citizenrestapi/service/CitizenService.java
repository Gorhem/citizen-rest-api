package com.gorhem.citizenrestapi.service;

import com.gorhem.citizenrestapi.entity.Citizen;
import com.gorhem.citizenrestapi.model.CitizenRequestDto;
import com.gorhem.citizenrestapi.model.CitizenResponseDto;
import com.gorhem.citizenrestapi.model.FilterDto;

import java.util.List;

public interface CitizenService {

    Citizen findById(Long id);

    Citizen save(CitizenRequestDto citizen);

    List<CitizenResponseDto> search(List<FilterDto> filters);

    Long getTotalCount();
}
