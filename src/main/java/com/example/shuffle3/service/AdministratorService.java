package com.example.shuffle3.service;

import com.example.shuffle3.Entity.Administrator;
import com.example.shuffle3.repository.AdministratorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {
    private  final AdministratorRepo administratorRepo;

    public AdministratorService(AdministratorRepo administratorRepo) {
        this.administratorRepo = administratorRepo;
    }
    public Administrator create(Administrator administrator){
        return administratorRepo.save(administrator);
    }
    public List<Administrator> getAll(){
        return administratorRepo.findAll();
    }
    public Administrator getById(Long id){
        return  administratorRepo.findById(id).orElse(null);
    }
}

