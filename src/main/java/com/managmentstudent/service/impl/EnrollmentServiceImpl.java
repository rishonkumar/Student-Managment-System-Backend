package com.managmentstudent.service.impl;

import com.managmentstudent.dtos.request.EnrollmentCreateRequest;
import com.managmentstudent.dtos.request.EnrollmentUpdateRequest;
import com.managmentstudent.dtos.response.EnrollmentResponse;
import com.managmentstudent.dtos.response.StudentBasicResponse;
import com.managmentstudent.dtos.response.SubjectBasicResponse;
import com.managmentstudent.exception.BusinessException;
import com.managmentstudent.exception.ResourceNotFoundException;
import com.managmentstudent.model.Enrollment;
import com.managmentstudent.model.Student;
import com.managmentstudent.model.Subject;
import com.managmentstudent.repository.EnrollmentRepository;
import com.managmentstudent.repository.StudentRepository;
import com.managmentstudent.repository.SubjectRepository;
import com.managmentstudent.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepository studentRepository,
                                 SubjectRepository subjectRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public EnrollmentResponse createEnrollment(EnrollmentCreateRequest request) {
        // Verify student and subject exist
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        // Check if enrollment already exists
        boolean isEnrollmentPresent = enrollmentRepository.findByStudentIdAndSubjectId(
                request.getStudentId(), request.getSubjectId()).isPresent();

        if (isEnrollmentPresent) {
            throw new BusinessException("Student is already enrolled in this subject");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setSubject(subject);
        enrollment.setFees(request.getFees());
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setFeesPaid(false);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToResponse(savedEnrollment);
    }

    @Override
    public EnrollmentResponse updateEnrollment(Long id, EnrollmentUpdateRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

        if (request.getFees() != null && request.getFees() < 0) {
            throw new BusinessException("Fees cannot be negative");
        }
        enrollment.setFees(request.getFees());
        enrollment.setFeesPaid(request.getFeesPaid());
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return convertToResponse(updatedEnrollment);

    }

    @Override
    public void deleteEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId) {
        List<Enrollment>enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateTotalFeesPaid(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .filter(e -> Boolean.TRUE.equals(e.getFeesPaid()))
                .mapToDouble(Enrollment::getFees)
                .sum();
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    private EnrollmentResponse convertToResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());

        // Convert Student to StudentBasicResponse
        StudentBasicResponse studentResponse = new StudentBasicResponse();
        studentResponse.setId(enrollment.getStudent().getId());
        studentResponse.setFirstName(enrollment.getStudent().getFirstName());
        studentResponse.setLastName(enrollment.getStudent().getLastName());
        response.setStudent(studentResponse);

        // Convert Subject to SubjectBasicResponse
        SubjectBasicResponse subjectResponse = new SubjectBasicResponse();
        subjectResponse.setId(enrollment.getSubject().getId());
        subjectResponse.setSubjectName(enrollment.getSubject().getSubjectName());
        subjectResponse.setSubjectCode(enrollment.getSubject().getSubjectCode());
        response.setSubject(subjectResponse);

        response.setFees(enrollment.getFees());
        response.setEnrollmentDate(enrollment.getEnrollmentDate());
        response.setFeesPaid(enrollment.getFeesPaid());

        return response;
    }
}
