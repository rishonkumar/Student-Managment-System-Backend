package com.managmentstudent.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private Long id;
    private StudentBasicResponse student;
    private SubjectBasicResponse subject;
    private Double fees;
    private LocalDate enrollmentDate;
    private Boolean feesPaid;

    // Getters, setters, no-args constructor
}
