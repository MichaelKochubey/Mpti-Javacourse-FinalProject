package com.example.biblioteka.database;

import com.example.biblioteka.model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Integer> {
    List<Reading> findByPersonId(Integer personId);
    List<Reading> findByBookId(Integer bookId);
}
