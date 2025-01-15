package com.managmentstudent.repository;

import com.managmentstudent.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    List<Student> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:searchTerm% OR s.lastName LIKE %:searchTerm% OR s.email LIKE %:searchTerm%")
    List<Student> searchStudent(@Param("searchTerm") String searchTerm);
}
