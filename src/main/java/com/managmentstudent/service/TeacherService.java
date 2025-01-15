package com.managmentstudent.service;

import com.managmentstudent.dtos.request.TeacherCreateRequest;
import com.managmentstudent.dtos.request.TeacherUpdateRequest;
import com.managmentstudent.dtos.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    TeacherResponse createTeacher(TeacherCreateRequest request);
    TeacherResponse updateTeacher(Long id, TeacherUpdateRequest request);
    void deleteTeacher(Long id);
    TeacherResponse getTeacherById(Long id);
    List<TeacherResponse> getAllTeachers();
}
