package com.example.shuffle3.repository;

import com.example.shuffle3.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface SubjectRepo extends JpaRepository<Subject ,Long> {
}
