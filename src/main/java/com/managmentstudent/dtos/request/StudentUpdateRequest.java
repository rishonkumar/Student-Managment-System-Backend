package com.managmentstudent.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    // Getters, setters, no-args constructor
}
