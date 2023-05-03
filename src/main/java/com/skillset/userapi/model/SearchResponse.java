package com.skillset.userapi.model;

import lombok.*;

public interface SearchResponse {

    Long getEmployeeId();

    Long getSkillId();

    String getEmployeeName();

    String getSkillName();

    Integer getRating();

    String getDepartment();
}
