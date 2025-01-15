package com.managmentstudent.service;

import com.managmentstudent.dtos.request.StudentCreateRequest;
import com.managmentstudent.dtos.request.StudentUpdateRequest;
import com.managmentstudent.dtos.response.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse createStudent(StudentCreateRequest request);
    StudentResponse updateStudent(Long id, StudentUpdateRequest request);
    void deleteStudent(Long id);
    StudentResponse getStudentById(Long id);
    List<StudentResponse> getAllStudents();
    List<StudentResponse> searchStudents(String searchTerm);
}
