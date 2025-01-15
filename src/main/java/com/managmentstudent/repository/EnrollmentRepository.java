package com.managmentstudent.repository;

import com.managmentstudent.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findBySubjectId(Long subjectId);
    Optional<Enrollment> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
