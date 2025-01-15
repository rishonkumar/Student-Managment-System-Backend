package com.managmentstudent.service;

import com.managmentstudent.dtos.request.SubjectCreateRequest;
import com.managmentstudent.dtos.request.SubjectUpdateRequest;
import com.managmentstudent.dtos.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    SubjectResponse createSubject(SubjectCreateRequest request);
    SubjectResponse updateSubject(Long id, SubjectUpdateRequest request);
    void deleteSubject(Long id);
    List<SubjectResponse> getAllSubjects();
}
