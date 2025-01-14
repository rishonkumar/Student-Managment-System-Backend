package com.managmentstudent.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // Getters, setters, no-args constructor
}

