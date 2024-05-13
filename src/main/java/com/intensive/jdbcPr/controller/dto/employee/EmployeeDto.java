package com.intensive.jdbcPr.controller.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private Long departmentId;
    private Long personalCardId;
}
