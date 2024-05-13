package com.intensive.jdbcPr.service.api;

import com.intensive.jdbcPr.controller.dto.employee.EmployeesWithProjectsDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.entity.Employee;
import com.intensive.jdbcPr.entity.Project;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getExactEmployee(String firstName, String lastName);

    void saveEmployeeList(List<EmployeeDto> employeeList);

    void save(EmployeeDto employee);

    void deleteExact(String firstName, String lastName);

    void updateEmployeeDepartment(String firstName, String lastName, Long departmentId);

    void deleteAll();

    void updateEmployeePersonalCardPosition(String firstName, String lastName, String position);

    List<Project> getAllProjectsByEmployee(String firstName, String lastName);

    List<EmployeesWithProjectsDto> getAllEmployeesWithProjects();
}
