package com.intensive.jdbcPr.controller.dto.personalCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCardDto {
    private String male;
    private Integer salary;
    private Integer age;
    private String phoneNumber;
    private String currentAddress;
    private String position;
}
