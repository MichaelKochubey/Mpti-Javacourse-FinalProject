package com.example.biblioteka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Book")
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "yearofpubl")
    private Integer yearOfPublishment;

    @ManyToOne
    @JoinColumn(name = "authorid")
    private Author author;

    private String description;

    public Book(String name, Integer yearOfPublishment, Author author, String description) {
        this.name = name;
        this.yearOfPublishment = yearOfPublishment;
        this.author = author;
        this.description = description;
    }
}
