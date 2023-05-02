package com.skillset.userapi.converter;

import com.skillset.userapi.domain.Skill;
import com.skillset.userapi.model.SkillRequest;
import com.skillset.userapi.model.SkillResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SkillConverter {

    public SkillResponse convert(Skill source) {
        return SkillResponse.builder().id(source.getId()).skillName(source.getSkillName()).build();
    }

    public List<SkillResponse> toResponses(List<Skill> skillsList) {
        return skillsList.stream().map(this::convert).collect(Collectors.toList());
    }

    public Skill convertToEntity(SkillRequest skillRequest) {
        return Skill.builder().skillName(skillRequest.getSkillName()).build();
    }
}
