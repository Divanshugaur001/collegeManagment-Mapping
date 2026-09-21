package com.example.shuffle3.service;

import com.example.shuffle3.Entity.Administrator;
import com.example.shuffle3.Entity.Student;
import com.example.shuffle3.repository.AdministratorRepo;
import com.example.shuffle3.repository.StudentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    public  final StudentRepo studentRepo;
    public final AdministratorRepo administratorRepo;

    public StudentService(StudentRepo studentRepo,AdministratorRepo administratorRepo) {
        this.studentRepo = studentRepo;
        this.administratorRepo=administratorRepo;
    }
    public Student create(Student student){
        return studentRepo.save(student);
    }
    public List<Student> getAll(){
        return studentRepo.findAll();
    }
    public Student getById(Long id){
        return  studentRepo.findById(id).orElse(null);
    }

    public Student mapAdministrator(Long id,Long administrator_id){
        Administrator administrator= administratorRepo.findById(administrator_id).orElseThrow(()->new EntityNotFoundException("not found"));
        Student student=studentRepo.findById(id).orElseThrow(()->new EntityNotFoundException("not found"));
        student.setAdministrator(administrator);
        administrator.setStudent(student);
        return studentRepo.save(student);
    }
}
