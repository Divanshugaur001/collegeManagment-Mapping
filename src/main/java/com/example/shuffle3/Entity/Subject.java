package com.example.shuffle3.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn( name = "proffesor_id")
    private  Professor professor;
}
