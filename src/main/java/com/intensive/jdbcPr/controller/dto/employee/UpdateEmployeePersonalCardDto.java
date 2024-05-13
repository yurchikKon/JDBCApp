package com.intensive.jdbcPr.controller.dto.employee;

import lombok.Data;

@Data
public class UpdateEmployeePersonalCardDto {
    private String firstName;
    private String lastName;
    private String position;
}
