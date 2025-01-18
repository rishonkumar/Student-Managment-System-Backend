package com.managmentstudent.service.impl;

import com.managmentstudent.dtos.request.StudentCreateRequest;
import com.managmentstudent.dtos.request.StudentUpdateRequest;
import com.managmentstudent.dtos.response.EnrollmentResponse;
import com.managmentstudent.dtos.response.StudentResponse;
import com.managmentstudent.dtos.response.SubjectBasicResponse;
import com.managmentstudent.exception.BusinessException;
import com.managmentstudent.exception.ResourceNotFoundException;
import com.managmentstudent.model.Enrollment;
import com.managmentstudent.model.Student;
import com.managmentstudent.repository.EnrollmentRepository;
import com.managmentstudent.repository.StudentRepository;
import com.managmentstudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public StudentResponse createStudent(StudentCreateRequest request) {
        System.out.println("Received request to create student: " + request.getEmail());


        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Student with email already exists");
        }

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());

        Student savedStudent = studentRepository.save(student);
        System.out.println("Saved student with ID: " + savedStudent.getId());

        return convertToResponse(savedStudent);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setAddress(request.getAddress());

        Student updatedStudent = studentRepository.save(student);
        return convertToResponse(updatedStudent);
    }

    private StudentResponse convertToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setEmail(student.getEmail());
        response.setPhoneNumber(student.getPhoneNumber());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setAddress(student.getAddress());

        // If you need enrollment information
        if (student.getEnrollments() != null) {
            List<EnrollmentResponse> enrollmentResponses = student.getEnrollments()
                    .stream()
                    .map(this::convertToEnrollmentResponse)
                    .collect(Collectors.toList());
            response.setEnrollments(enrollmentResponses);
        }
        return response;
    }

    private EnrollmentResponse convertToEnrollmentResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setFees(enrollment.getFees());
        response.setEnrollmentDate(enrollment.getEnrollmentDate());
        response.setFeesPaid(enrollment.getFeesPaid());

        // Add basic subject info
        SubjectBasicResponse subjectResponse = new SubjectBasicResponse();
        subjectResponse.setId(enrollment.getSubject().getId());
        subjectResponse.setSubjectName(enrollment.getSubject().getSubjectName());
        subjectResponse.setSubjectCode(enrollment.getSubject().getSubjectCode());
        response.setSubject(subjectResponse);

        return response;
    }


    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        studentRepository.delete(student);

    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return convertToResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        //.map(student -> convertToResponse(student)) check Notes for reference
        return studentRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }


    @Override
    public List<StudentResponse> searchStudents(String searchTerm) {
        if(searchTerm == null || searchTerm.isEmpty()) {
            throw new BusinessException("Search term cannot be empty");
        }
        return studentRepository.searchStudent(searchTerm).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
}
