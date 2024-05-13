package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.controller.dto.project.ProjectDto;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.repository.api.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptySet;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    private static final Project FIRST_PROJECT = new Project(1L, "First", "Company", emptySet());
    private static final Project SECOND_PROJECT = new Project(2L, "Second", "Company", emptySet());
    private static final EmployeeDto EMPLOYEE_DTO_IVAN = new EmployeeDto("Ivan",
        "Ivanov", 1L, 1L);
    private static final EmployeeDto EMPLOYEE_DTO_YURI = new EmployeeDto("Yuri",
        "Kondratenko", 2L, 2L);
    private static final ProjectDto FIRST_DTO = new ProjectDto("First", "Company");
    private static final String FIRST_NAME = "FIRST";
    private static final String OLD_NAME = "First";
    private static final String NEW_NAME = "Third";

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    ProjectServiceImpl projectService;

    @Test
    void getAll() {
        when(projectRepository.getAll()).thenReturn(of(FIRST_PROJECT, SECOND_PROJECT));

        List<Project> expected = of(FIRST_PROJECT, SECOND_PROJECT);

        assertEquals(expected, projectService.getAll());
    }

    @Test
    void getExact() {
        when(projectRepository.getExact(FIRST_NAME)).thenReturn(FIRST_PROJECT);

        assertEquals(FIRST_PROJECT, projectService.getExact(FIRST_NAME));
    }

    @Test
    void save() {
        projectService.save(FIRST_DTO);

        verify(projectRepository, times(1)).save(FIRST_DTO);
    }

    @Test
    void update() {
        projectService.update(OLD_NAME, NEW_NAME);

        verify(projectRepository, times(1)).update(OLD_NAME, NEW_NAME);
    }

    @Test
    void deleteExact() {
        projectService.deleteExact(FIRST_NAME);

        verify(projectRepository, times(1)).deleteExact(FIRST_NAME);
    }

    @Test
    void deleteAll() {
        projectService.deleteAll();

        verify(projectRepository, times(1)).deleteAll();
    }

    @Test
    void getAllEmployeesByProject() {
        when(projectRepository.getAllEmployeesByProject(FIRST_NAME)).thenReturn(of(EMPLOYEE_DTO_IVAN, EMPLOYEE_DTO_YURI));

        List<EmployeeDto> expected = of(EMPLOYEE_DTO_IVAN, EMPLOYEE_DTO_YURI);

        assertEquals(expected, projectService.getAllEmployeesByProject(FIRST_NAME));
    }
}