package com.managmentstudent.service;

import com.managmentstudent.dtos.request.EnrollmentCreateRequest;
import com.managmentstudent.dtos.request.EnrollmentUpdateRequest;
import com.managmentstudent.dtos.response.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse createEnrollment(EnrollmentCreateRequest request);

    EnrollmentResponse updateEnrollment(Long id, EnrollmentUpdateRequest request);

    void deleteEnrollment(Long id);

    List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId);

    Double calculateTotalFeesPaid(Long studentId);

    List<EnrollmentResponse> getAllEnrollments();
}
