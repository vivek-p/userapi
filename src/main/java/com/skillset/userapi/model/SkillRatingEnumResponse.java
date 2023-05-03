package com.skillset.userapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillRatingEnumResponse {

    private Integer skillRatingCode;

    private String skillRating;
}
