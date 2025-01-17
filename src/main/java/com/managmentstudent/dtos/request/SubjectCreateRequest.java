package com.managmentstudent.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectCreateRequest {
    private String subjectName;
    private String subjectCode;
    private Integer credits;
}
