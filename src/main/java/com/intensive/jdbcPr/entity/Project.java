package com.intensive.jdbcPr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Long id;
    private String name;
    private String company;
    private Set<Employee> employees;
}
