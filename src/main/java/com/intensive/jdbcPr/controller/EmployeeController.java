package com.intensive.jdbcPr.controller;

import com.intensive.jdbcPr.controller.dto.employee.EmployeesWithProjectsDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.controller.dto.employee.UpdateEmployeePersonalCardDto;
import com.intensive.jdbcPr.entity.Employee;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.service.api.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/getAll")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/getExact")
    public Employee getExactEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        return employeeService.getExactEmployee(firstName, lastName);
    }

    @PostMapping("/saveList")
    public ResponseEntity<String> saveEmployeeList(@RequestBody List<EmployeeDto> employeeList) {
        employeeService.saveEmployeeList(employeeList);
        return ResponseEntity.ok("New employees were created");
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody EmployeeDto employee) {
        employeeService.save(employee);
        return ResponseEntity.ok("New employee was created");
    }

    @PostMapping("/deleteExact")
    public ResponseEntity<String> deleteExact(String firstName, String lastName) {
        employeeService.deleteExact(firstName, lastName);
        return ResponseEntity.ok("Employee was deleted");
    }

    @PostMapping("/updateDepartment")
    public ResponseEntity<String> updateEmployeeDepartment(String firstName, String lastName, Long departmentId) {
        employeeService.updateEmployeeDepartment(firstName, lastName, departmentId);
        return ResponseEntity.ok("Employee was updated");
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity.ok("All employees were deleted");
    }

    @GetMapping("/getProjectByEmployee")
    public List<Project> getAllProjectsByEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        return employeeService.getAllProjectsByEmployee(firstName, lastName);
    }

    @PostMapping("/updateEmployeePersonalCard")
    public ResponseEntity<String> updateEmployeePersonalCard(@RequestBody UpdateEmployeePersonalCardDto personalCardDto) {
        employeeService.updateEmployeePersonalCardPosition(personalCardDto.getFirstName(), personalCardDto.getLastName(),
            personalCardDto.getPosition());
        return ResponseEntity.ok("Personal card of employee was updated");
    }

    @GetMapping("/getAllEmployeesWithProjects")
    public List<EmployeesWithProjectsDto> getAllEmployeesWithProjects() {
        return employeeService.getAllEmployeesWithProjects();
    }
}
