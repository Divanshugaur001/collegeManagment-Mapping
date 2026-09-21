package com.example.shuffle3.controller;

import com.example.shuffle3.Entity.Professor;
import com.example.shuffle3.Entity.Student;
import com.example.shuffle3.Entity.Subject;
import com.example.shuffle3.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @GetMapping
    public List<Subject> getAll(){
        return  subjectService.getAll();

    }
    @GetMapping("/{id}")
    public Subject getByid(@PathVariable Long id){
        return subjectService.getBYId(id);
    }
    @PostMapping
    public Subject create(@RequestBody Subject subject){
        return  subjectService.create(subject);
    }
    @PutMapping("/{subjectId}/professor/{professorId}")
    public Professor mapAdministrator(
            @PathVariable Long subjectId,
            @PathVariable Long professorId) {

        return subjectService.mapSubject(subjectId,professorId);
    }
}
