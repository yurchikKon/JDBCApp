package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.controller.dto.project.ProjectDto;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.repository.api.ProjectRepository;
import com.intensive.jdbcPr.service.api.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    @Override
    public Project getExact(String name) {
        return projectRepository.getExact(name);
    }

    @Override
    public void save(ProjectDto project) {
        projectRepository.save(project);
    }

    @Override
    public void update(String oldName, String newName) {
        projectRepository.update(oldName, newName);
    }

    @Override
    public void deleteExact(String name) {
        projectRepository.deleteExact(name);
    }

    @Override
    public void deleteAll() {
        projectRepository.deleteAll();
    }

    @Override
    public List<EmployeeDto> getAllEmployeesByProject(String name) {
        return projectRepository.getAllEmployeesByProject(name);
    }
}
