package com.example.shuffle3.repository;

import com.example.shuffle3.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
}
