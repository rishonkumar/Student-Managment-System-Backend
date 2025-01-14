package com.managmentstudent.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectBasicResponse {
    private Long id;
    private String subjectName;
    private String subjectCode;

    // Getters, setters, no-args constructor
}
