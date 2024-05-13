package com.intensive.jdbcPr.repository.api;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.entity.Department;

import java.util.List;

public interface DepartmentRepository {
    List<Department> getAll();

    Department getExact(String name);

    void save(DepartmentDto department);

    void update(String oldName, String newName);

    void deleteExact(String name);

    void deleteAll();
}
