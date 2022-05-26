package com.gorhem.citizenrestapi.controller;

import com.gorhem.citizenrestapi.entity.Citizen;
import com.gorhem.citizenrestapi.model.CitizenRequestDto;
import com.gorhem.citizenrestapi.model.CitizenResponseDto;
import com.gorhem.citizenrestapi.model.FilterDto;
import com.gorhem.citizenrestapi.service.CitizenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("citizen")
@RestController
public class CitizenController {

    private final CitizenService service;

    public CitizenController(CitizenService service) {
        this.service = service;
    }

    @GetMapping("findById/{id}")
    public Citizen findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping("search")
    public List<CitizenResponseDto> search(@RequestBody List<FilterDto> filters){
        return service.search(filters);
    }

    @PostMapping ("save")
    public Citizen save(@RequestBody CitizenRequestDto citizen){
        return service.save(citizen);
    }

    @GetMapping("totalCount")
    public Long getTotalCount(){
        return service.getTotalCount();
    }
}
