package com.example.biblioteka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "Reading")
@NoArgsConstructor
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "personid", nullable = false)
    private Person person;

    @OneToOne
    @JoinColumn(name = "bookid", nullable = false)
    private Book book;

    public Reading(Person person, Book book) {
        this.person = person;
        this.book = book;
    }
}
