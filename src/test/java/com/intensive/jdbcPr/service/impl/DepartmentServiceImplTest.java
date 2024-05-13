package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.repository.api.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    private static final Department FIRST_DEPARTMENT = new Department(1L, "First");
    private static final Department SECOND_DEPARTMENT = new Department(2L, "Second");
    private static final DepartmentDto FIRST_DTO = new DepartmentDto("FirstDto");
    private static final String FIRST_NAME = "FIRST";
    private static final String OLD_NAME = "First";
    private static final String NEW_NAME = "Third";

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Test
    void getAll() {
        when(departmentRepository.getAll()).thenReturn(of(FIRST_DEPARTMENT, SECOND_DEPARTMENT));

        List<Department> expected = of(FIRST_DEPARTMENT, SECOND_DEPARTMENT);

        assertEquals(expected, departmentService.getAll());
    }

    @Test
    void getExact() {
        when(departmentRepository.getExact(FIRST_NAME)).thenReturn(FIRST_DEPARTMENT);

        assertEquals(FIRST_DEPARTMENT, departmentService.getExact(FIRST_NAME));
    }

    @Test
    void save() {
        departmentService.save(FIRST_DTO);

        verify(departmentRepository, times(1)).save(FIRST_DTO);
    }

    @Test
    void update() {
        departmentService.update(OLD_NAME, NEW_NAME);

        verify(departmentRepository, times(1)).update(OLD_NAME, NEW_NAME);
    }

    @Test
    void deleteExact() {
        departmentService.deleteExact(FIRST_NAME);

        verify(departmentRepository, times(1)).deleteExact(FIRST_NAME);
    }

    @Test
    void deleteAll() {
        departmentService.deleteAll();

        verify(departmentRepository, times(1)).deleteAll();
    }
}