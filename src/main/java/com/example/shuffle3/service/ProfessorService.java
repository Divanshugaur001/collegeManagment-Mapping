package com.example.shuffle3.service;

import com.example.shuffle3.Entity.Professor;
import com.example.shuffle3.Entity.Student;
import com.example.shuffle3.repository.ProfessorRepo;
import com.example.shuffle3.repository.StudentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepo professorRepo;
    private final StudentRepo studentRepo;

    public ProfessorService(ProfessorRepo professorRepo, StudentRepo studentRepo) {
        this.professorRepo = professorRepo;
        this.studentRepo = studentRepo;
    }

    public Professor create(Professor professor) {
        return professorRepo.save(professor);
    }

    public List<Professor> getAll() {
        return professorRepo.findAll();
    }

    public Professor getByID(Long id) {
        return professorRepo.findById(id).orElse(null);
    }

    public Professor mapStudent(Long professorId, Long studentId) {

        Professor professor = professorRepo.findById(professorId)
                .orElseThrow(() -> new EntityNotFoundException("Professor not found"));

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        professor.getStudents().add(student);
        student.getProfessors().add(professor);

        return professorRepo.save(professor);
    }
}