package com.intensive.jdbcPr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCard {
    private Long id;
    private String male;
    private int age;
    private int salary;
    private String position;
    private String phoneNumber;
    private String currentAddress;
}
