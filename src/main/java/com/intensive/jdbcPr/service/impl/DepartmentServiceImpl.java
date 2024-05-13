package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.repository.api.DepartmentRepository;
import com.intensive.jdbcPr.service.api.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAll() {
        return departmentRepository.getAll();
    }

    @Override
    public Department getExact(String name) {
        return departmentRepository.getExact(name);
    }

    @Override
    public void save(DepartmentDto department) {
        departmentRepository.save(department);
    }

    @Override
    public void update(String oldName, String newName) {
        departmentRepository.update(oldName, newName);
    }

    @Override
    public void deleteExact(String name) {
        departmentRepository.deleteExact(name);
    }

    @Override
    public void deleteAll() {
        departmentRepository.deleteAll();
    }
}
