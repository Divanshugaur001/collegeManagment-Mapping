package com.example.shuffle3.controller;

import com.example.shuffle3.Entity.Administrator;
import com.example.shuffle3.Entity.Student;
import com.example.shuffle3.service.AdministratorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorConntroller {
    private final AdministratorService administratorService;

    public AdministratorConntroller(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @PostMapping
    public Administrator create(@RequestBody Administrator administrator){
        return administratorService.create(administrator);
    }
    @GetMapping
    public List<Administrator> getAll(){
        return administratorService.getAll();
    }
    @GetMapping("/{id}")
    public Administrator getById(@PathVariable  Long id){
        return administratorService.getById(id);
    }
}
