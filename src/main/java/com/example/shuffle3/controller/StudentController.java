package com.example.shuffle3.controller;

import com.example.shuffle3.Entity.Student;
import com.example.shuffle3.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }
    @GetMapping
    public List<Student> getAll(){
        return studentService.getAll();
    }
    @GetMapping("/{id}")
    public Student getById(@PathVariable  Long id){
        return studentService.getById(id);
    }
    @PutMapping("/{studentId}/administrator/{administratorId}")
    public Student mapAdministrator(
            @PathVariable Long studentId,
            @PathVariable Long administratorId) {

        return studentService.mapAdministrator(studentId, administratorId);
    }

}
