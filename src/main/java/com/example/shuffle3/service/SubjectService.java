package com.example.shuffle3.service;

import com.example.shuffle3.Entity.Professor;
import com.example.shuffle3.Entity.Subject;
import com.example.shuffle3.repository.ProfessorRepo;
import com.example.shuffle3.repository.SubjectRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final ProfessorRepo professorRepo;

    public SubjectService(SubjectRepo subjectRepo, ProfessorRepo professorRepo) {
        this.subjectRepo = subjectRepo;
        this.professorRepo = professorRepo;
    }
    public Subject create(Subject subject){
        return  subjectRepo.save(subject);
    }
    public Subject getBYId(Long id){
     return subjectRepo.findById(id).orElse(null);
    }
    public List<Subject> getAll(){
        return  subjectRepo.findAll();
    }
    public Professor mapSubject(Long professorId, Long subjectId) {

        Professor professor = professorRepo.findById(professorId)
                .orElseThrow(() -> new EntityNotFoundException("Professor not found"));

        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        professor.getSubjects().add(subject);
        subject.setProfessor(professor);

        return professorRepo.save(professor);
    }
}
