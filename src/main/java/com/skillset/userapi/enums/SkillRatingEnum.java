package com.skillset.userapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum SkillRatingEnum {

    AWARENESS(1),WORKING(2),PRACTITIONER(3),EXPERT(4);
    int code;

    public static SkillRatingEnum getSkillRatingType(Integer value) {
        if (Objects.isNull(value)){
            return null;
        }

        Optional<SkillRatingEnum> hasSkillRating = Arrays.asList(SkillRatingEnum.values())
                .stream().filter(skillRating -> skillRating.getCode() == value).findFirst();

        if(hasSkillRating.isPresent()) {
            return hasSkillRating.get();
        } else {
            return null;
        }
    }

    public static Integer getSkillRatingFromKey(String key) {
        if (Objects.isNull(key)){
            return null;
        }

        Optional<SkillRatingEnum> hasSkillRating = Arrays.asList(SkillRatingEnum.values())
                .stream().filter(skillRating -> skillRating.name() == key).findFirst();

        if(hasSkillRating.isPresent()) {
            return hasSkillRating.get().getCode();
        } else {
            return null;
        }
    }
}
