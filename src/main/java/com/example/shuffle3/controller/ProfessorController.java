package com.example.shuffle3.controller;

import com.example.shuffle3.Entity.Professor;
import com.example.shuffle3.service.ProfessorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
       this. professorService = professorService;
    }
    @PostMapping
    public Professor create(@RequestBody Professor professor){
        return professorService.create(professor);
    }
    @GetMapping
    public List<Professor>  getAll(){
        return professorService.getAll();
    }
    @GetMapping("/{id}")
    public Professor findByID(@PathVariable Long id){
     return professorService.getByID(id);
    }
    @PutMapping("/{id}/student/{student_id}")
    public Professor mapStudent(@PathVariable Long id,@PathVariable Long student_id){
        return professorService.mapStudent(id, student_id);
    }
}
