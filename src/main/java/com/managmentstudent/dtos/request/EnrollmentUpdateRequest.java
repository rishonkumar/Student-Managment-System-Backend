package com.managmentstudent.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentUpdateRequest {
    private Double fees;
    private Boolean feesPaid;

    // Getters, setters, no-args constructor
}
