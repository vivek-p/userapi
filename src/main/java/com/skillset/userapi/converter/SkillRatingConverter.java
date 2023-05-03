package com.skillset.userapi.converter;

import com.skillset.userapi.domain.SkillRating;
import com.skillset.userapi.enums.SkillRatingEnum;
import com.skillset.userapi.model.SearchResponse;
import com.skillset.userapi.model.SkillRatingEnumResponse;
import com.skillset.userapi.model.SkillRatingResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SkillRatingConverter {

    public SkillRatingResponse convert(SkillRating skillRating) {
        SkillRatingResponse response = SkillRatingResponse.builder().skillId(skillRating.getSkill().getId())
                .skillName(skillRating.getSkill().getSkillName())
                .skillRating(SkillRatingEnum.getSkillRatingType(skillRating.getRating()).name()).build();
        return response;
    }

    public List<SkillRatingResponse> toResponses(List<SkillRating> list) {
        return list.stream().map(this::convert).collect(Collectors.toList());
    }

    public SkillRatingEnumResponse convertEnumToResponse(String name, Integer code) {
        return SkillRatingEnumResponse.builder().skillRating(name).skillRatingCode(code).build();
    }

    public List<SkillRatingEnumResponse> convertEnumToResponses() {
        return Arrays.asList(SkillRatingEnum.values()).stream()
                .map(item -> this.convertEnumToResponse(item.name(), item.getCode())).collect(Collectors.toList());
    }

    public List<SkillRatingResponse> convertSearchResultToResponse(List<SearchResponse> list) {
        return list.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public SkillRatingResponse convertToResponse(SearchResponse searchResponse) {
        return SkillRatingResponse.builder().skillId(searchResponse.getSkillId()).skillName(searchResponse.getSkillName())
                .skillRating(SkillRatingEnum.getSkillRatingType(searchResponse.getRating()).name()).build();
    }


}
