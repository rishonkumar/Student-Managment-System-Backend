package com.managmentstudent.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {
    private Long id;
    private String subjectName;
    private String subjectCode;
    private Integer credits;
    private TeacherResponse teacher;

    // Getters, setters, no-args constructor
}
