package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.employee.EmployeesWithProjectsDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.entity.Employee;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.repository.api.EmployeeRepository;
import com.intensive.jdbcPr.service.api.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getExactEmployee(String firstName, String lastName) {
        return employeeRepository.getExactEmployee(firstName, lastName);
    }

    @Override
    public void saveEmployeeList(List<EmployeeDto> employeeList) {
        employeeRepository.saveEmployeeList(employeeList);
    }

    @Override
    public void save(EmployeeDto employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteExact(String firstName, String lastName) {
        employeeRepository.deleteExact(firstName, lastName);
    }

    @Override
    public void updateEmployeeDepartment(String firstName, String lastName, Long departmentId) {
        employeeRepository.updateEmployeeDepartment(firstName, lastName, departmentId);
    }

    @Override
    public void deleteAll() {
        employeeRepository.deleteAll();
    }

    @Override
    public void updateEmployeePersonalCardPosition(String firstName, String lastName, String position) {
        employeeRepository.updateEmployeePersonalCardPosition(firstName, lastName, position);
    }

    @Override
    public List<Project> getAllProjectsByEmployee(String firstName, String lastName) {
        return employeeRepository.getAllProjectsByEmployee(firstName, lastName);
    }

    @Override
    public List<EmployeesWithProjectsDto> getAllEmployeesWithProjects() {
        return employeeRepository.getAllEmployeesWithProjects();
    }
}
