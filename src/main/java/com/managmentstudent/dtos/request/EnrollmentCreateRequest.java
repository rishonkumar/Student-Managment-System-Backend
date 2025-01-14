package com.managmentstudent.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentCreateRequest {
    private Long studentId;
    private Long subjectId;
    private Double fees;

    // Getters, setters, no-args constructor
}
