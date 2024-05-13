package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeesWithProjectsDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.entity.Employee;
import com.intensive.jdbcPr.entity.PersonalCard;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.repository.api.EmployeeRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@Ignore
class EmployeeServiceImplTest {

    private static final Employee EMPLOYEE_IVAN = new Employee(1L, "Ivan", "Ivanov",
        new Department(), new PersonalCard(), emptySet());
    private static final Employee EMPLOYEE_YURI = new Employee(2L, "Yuri", "Kondratenko",
        new Department(), new PersonalCard(), emptySet());
    private static final EmployeeDto EMPLOYEE_DTO_IVAN = new EmployeeDto("Ivan",
        "Ivanov", 1L, 1L);
    private static final EmployeeDto EMPLOYEE_DTO_YURI = new EmployeeDto("Yuri",
        "Kondratenko", 2L, 2L);
    private static final String EMPLOYEE_FIRST_NAME_IVAN = "Ivan";
    private static final String EMPLOYEE_LAST_NAME_IVANOV = "Ivanov";
    private static final String IVANOV_POSITION = "Manager";
    private static final Project FIRST_PROJECT = new Project(1L, "First", "Company", Set.of(EMPLOYEE_IVAN));
    private static final Project SECOND_PROJECT = new Project(2L, "Second", "Company", Set.of(EMPLOYEE_IVAN));
    private static final EmployeesWithProjectsDto EMPLOYEE_IVANOV_WITH_PROJECT = new EmployeesWithProjectsDto("Ivan",
        "Ivanov", "First", "Company");
    private static final EmployeesWithProjectsDto EMPLOYEE_YURI_WITH_PROJECT = new EmployeesWithProjectsDto("Yuri",
        "Kondratenko", "First", "Company");

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    void getAllEmployees() {
        when(employeeRepository.getAllEmployees()).thenReturn(of(EMPLOYEE_IVAN, EMPLOYEE_YURI));

        List<Employee> expected = of(EMPLOYEE_IVAN, EMPLOYEE_YURI);

        assertEquals(expected, employeeService.getAllEmployees());
    }

    @Test
    void getExactEmployee() {
        when(employeeRepository.getExactEmployee(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV)).thenReturn(EMPLOYEE_IVAN);

        assertEquals(EMPLOYEE_IVAN, employeeService.getExactEmployee(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV));
    }

    @Test
    void saveEmployeeList() {
        employeeService.saveEmployeeList(of(EMPLOYEE_DTO_IVAN, EMPLOYEE_DTO_YURI));

        verify(employeeRepository, times(1)).saveEmployeeList(of(EMPLOYEE_DTO_IVAN, EMPLOYEE_DTO_YURI));
    }

    @Test
    void save() {
        employeeService.save(EMPLOYEE_DTO_IVAN);

        verify(employeeRepository, times(1)).save(EMPLOYEE_DTO_IVAN);
    }

    @Test
    void deleteExact() {
        employeeService.deleteExact(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV);

        verify(employeeRepository, times(1)).deleteExact(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV);
    }

    @Test
    void updateEmployeeDepartment() {
        employeeService.updateEmployeeDepartment(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV,
            EMPLOYEE_IVAN.getDepartment().getId());

        verify(employeeRepository, times(1)).updateEmployeeDepartment(EMPLOYEE_FIRST_NAME_IVAN,
            EMPLOYEE_LAST_NAME_IVANOV, EMPLOYEE_IVAN.getDepartment().getId());
    }

    @Test
    void deleteAll() {
        employeeService.deleteAll();

        verify(employeeRepository, times(1)).deleteAll();
    }

    @Test
    void updateEmployeePersonalCardPosition() {
        employeeService.updateEmployeePersonalCardPosition(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV, IVANOV_POSITION);

        verify(employeeRepository, times(1))
            .updateEmployeePersonalCardPosition(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV, IVANOV_POSITION);
    }

    @Test
    void getAllProjectsByEmployee() {
        when(employeeRepository.getAllProjectsByEmployee(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV))
            .thenReturn(of(FIRST_PROJECT, SECOND_PROJECT));

        List<Project> expected = of(FIRST_PROJECT, SECOND_PROJECT);

        assertEquals(expected, employeeService.getAllProjectsByEmployee(EMPLOYEE_FIRST_NAME_IVAN, EMPLOYEE_LAST_NAME_IVANOV));
    }

    @Test
    void getAllEmployeesWithProjects() {
        when(employeeRepository.getAllEmployeesWithProjects()).thenReturn(of(EMPLOYEE_YURI_WITH_PROJECT,
            EMPLOYEE_IVANOV_WITH_PROJECT));

        List<EmployeesWithProjectsDto> expected = of(EMPLOYEE_YURI_WITH_PROJECT, EMPLOYEE_IVANOV_WITH_PROJECT);

        assertEquals(expected, employeeService.getAllEmployeesWithProjects());
    }
}