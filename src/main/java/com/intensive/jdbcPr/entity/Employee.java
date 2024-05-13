package com.intensive.jdbcPr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Department department;
    private PersonalCard personalCard;
    private Set<Project> projects;
}
