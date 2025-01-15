package com.managmentstudent.service.impl;

import com.managmentstudent.dtos.request.SubjectCreateRequest;
import com.managmentstudent.dtos.request.SubjectUpdateRequest;
import com.managmentstudent.dtos.response.SubjectResponse;
import com.managmentstudent.exception.BusinessException;
import com.managmentstudent.exception.ResourceNotFoundException;
import com.managmentstudent.model.Subject;
import com.managmentstudent.repository.StudentRepository;
import com.managmentstudent.repository.SubjectRepository;
import com.managmentstudent.repository.TeacherRepository;
import com.managmentstudent.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository,
                              StudentRepository studentRepository,
                              TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public SubjectResponse createSubject(SubjectCreateRequest request) {

        //subject code is unique
        if(subjectRepository.findBySubjectCode(request.getSubjectCode()).isPresent()){
            throw new BusinessException("Subject with code already exists");
        }

        Subject subject = new Subject();
        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());

        Subject savedSubject = subjectRepository.save(subject);
        return convertToResponse(savedSubject);
    }


    @Override
    public SubjectResponse updateSubject(Long id, SubjectUpdateRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        // Update subject details
        subject.setSubjectName(request.getSubjectName());
        subject.setCredits(request.getCredits());

        Subject updatedSubject = subjectRepository.save(subject);
        return convertToResponse(updatedSubject);
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        // Check if subject has any enrollments
        if (!subject.getEnrollments().isEmpty()) {
            throw new BusinessException("Cannot delete subject with active enrollments");
        }

        subjectRepository.delete(subject);
    }



    @Override
    public List<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private SubjectResponse convertToResponse(Subject subject) {
        SubjectResponse response = new SubjectResponse();
        response.setId(subject.getId());
        response.setSubjectName(subject.getSubjectName());
        response.setSubjectCode(subject.getSubjectCode());
        response.setCredits(subject.getCredits());
        return response;
    }
}
