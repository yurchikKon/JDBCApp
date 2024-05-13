package com.intensive.jdbcPr.service.api;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.controller.dto.project.ProjectDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.entity.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAll();

    Project getExact(String name);

    void save(ProjectDto project);

    void update(String oldName, String newName);

    void deleteExact(String name);

    void deleteAll();

    List<EmployeeDto> getAllEmployeesByProject(String name);
}
