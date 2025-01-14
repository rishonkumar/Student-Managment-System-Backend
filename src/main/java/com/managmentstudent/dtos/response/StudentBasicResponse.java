package com.managmentstudent.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentBasicResponse {
    private Long id;
    private String firstName;
    private String lastName;

    // Getters, setters, no-args constructor
}
