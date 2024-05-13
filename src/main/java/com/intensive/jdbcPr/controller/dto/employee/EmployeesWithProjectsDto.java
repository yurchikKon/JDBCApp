package com.intensive.jdbcPr.controller.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesWithProjectsDto {
    private String firstName;
    private String lastName;
    private String projectName;
    private String projectCompany;
}
