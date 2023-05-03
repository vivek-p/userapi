package com.skillset.userapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;

    private String employeeName;

    private String department;

    private List<SkillRatingResponse> skillset;


}
