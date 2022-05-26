package com.gorhem.citizenrestapi.service.impl;

import com.gorhem.citizenrestapi.entity.Citizen;
import com.gorhem.citizenrestapi.model.CitizenRequestDto;
import com.gorhem.citizenrestapi.model.CitizenResponseDto;
import com.gorhem.citizenrestapi.model.FilterDto;
import com.gorhem.citizenrestapi.repository.CitizenRepository;
import com.gorhem.citizenrestapi.repository.specification.CitizenSpecification;
import com.gorhem.citizenrestapi.service.CitizenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository repository;

    public CitizenServiceImpl(CitizenRepository repository){
        this.repository = repository;
    }

    @Override
    public Citizen findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Citizen not found"
        ));
    }

    @Override
    public Citizen save(CitizenRequestDto citizenRequestDto) {
        Citizen citizen = new Citizen();
        citizen.setIsCitizen(citizenRequestDto.getIsCitizen());
        citizen.setName(citizenRequestDto.getName());
        citizen.setHasDrivingLicense(citizenRequestDto.getHasDrivingLicense());
        citizen.setId(citizenRequestDto.getId());

        Set<Long> childrenIds = citizenRequestDto.getChildrenIds();
        if(childrenIds != null && childrenIds.size() > 0) {
            List<Citizen> children = repository.findAllByIdIn(childrenIds);
            if(children.size() == childrenIds.size()) {
                citizen.setChildrenCount(children.size());
                citizen = repository.save(citizen);
                repository.assignChildren(childrenIds, citizen.getId());
            } else {
                List<Long> foundIds = children.stream().map(Citizen::getId).collect(Collectors.toList());
                List<String> differences = childrenIds.stream()
                        .filter(id -> !foundIds.contains(id))
                        .map(String::valueOf)
                        .collect(Collectors.toList());
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The id values you specified as children could not be found as citizens: " +String.join("-",differences)
                );
            }
        } else {
            citizen.setChildrenCount(0);
            citizen = repository.save(citizen);
        }
        return citizen;
    }

    @Override
    public List<CitizenResponseDto> search(List<FilterDto> filters) {
        return repository.findAll(CitizenSpecification.getSpecificationFromFilters(filters))
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Long getTotalCount() {
        return repository.count();
    }

    private CitizenResponseDto convert(Citizen citizen){
        CitizenResponseDto citizenResponseDto = new CitizenResponseDto();
        citizenResponseDto.setId(citizen.getId());
        citizenResponseDto.setName(citizen.getName());
        citizenResponseDto.setIsCitizen(citizen.getIsCitizen());
        citizenResponseDto.setHasDrivingLicense(citizen.getHasDrivingLicense());
        citizenResponseDto.setChildrenCount(citizen.getChildrenCount());
        Map<Long, String> children =  citizen.getChildren().stream().collect(Collectors.toMap(Citizen::getId, Citizen::getName));
        citizenResponseDto.setChildren(children);
        return citizenResponseDto;
    }
}
