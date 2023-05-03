package com.skillset.userapi.model;

import com.skillset.userapi.enums.SkillRatingEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillRatingResponse {

    private Long skillId;

    private String skillName;

    private String skillRating;
}
