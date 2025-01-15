package com.managmentstudent.service.impl;

import com.managmentstudent.dtos.request.TeacherCreateRequest;
import com.managmentstudent.dtos.request.TeacherUpdateRequest;
import com.managmentstudent.dtos.response.TeacherResponse;
import com.managmentstudent.exception.BusinessException;
import com.managmentstudent.exception.ResourceNotFoundException;
import com.managmentstudent.model.Teacher;
import com.managmentstudent.repository.TeacherRepository;
import com.managmentstudent.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherResponse createTeacher(TeacherCreateRequest request) {
        // Check if email already exists
        if (teacherRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Teacher with this email already exists");
        }

        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhoneNumber(request.getPhoneNumber());

        Teacher savedTeacher = teacherRepository.save(teacher);
        return convertToResponse(savedTeacher);
    }

    @Override
    public TeacherResponse updateTeacher(Long id, TeacherUpdateRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setPhoneNumber(request.getPhoneNumber());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return convertToResponse(updatedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        teacherRepository.delete(teacher);
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        return convertToResponse(teacher);
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TeacherResponse convertToResponse(Teacher teacher) {
        TeacherResponse response = new TeacherResponse();
        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        response.setPhoneNumber(teacher.getPhoneNumber());
        return response;
    }
}
