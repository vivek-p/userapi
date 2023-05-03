package com.skillset.userapi.service;

import com.skillset.userapi.converter.SkillConverter;
import com.skillset.userapi.converter.SkillRatingConverter;
import com.skillset.userapi.domain.Skill;
import com.skillset.userapi.enums.SkillRatingEnum;
import com.skillset.userapi.exception.ErrorCodes;
import com.skillset.userapi.exception.NotFoundException;
import com.skillset.userapi.model.SkillRatingEnumResponse;
import com.skillset.userapi.model.SkillRequest;
import com.skillset.userapi.repository.SkillRatingRepository;
import com.skillset.userapi.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillService {

    private final SkillRepository skillRepository;

    private final SkillRatingRepository skillRatingRepository;

    private final ErrorCodes errorCodes;

    private final SkillConverter skillConverter;

    private final SkillRatingConverter skillRatingConverter;

    public Skill findById(Long id) {
        log.trace("find by {id}, ", id);
        return skillRepository.findById(id).orElseThrow(() -> new NotFoundException(errorCodes.getNotFound()));
    }

    public List<Skill> findByNameLike(String criteria, Pageable pageable) {
        log.trace("find by name like {criteria}, ", criteria);
        return skillRepository.findBySkillNameLikeIgnoreCase("%" + criteria + "%", pageable);
    }

    public List<Long> getSkillIdsBySkillNameLike(String criteria) {
        return skillRepository.findIdsBySkillNameLike(criteria);
    }

    public List<SkillRatingEnumResponse> getSkillRatings() {
        log.trace("Skill ratings");
        return skillRatingConverter.convertEnumToResponses();
    }

    public Skill createSkill(SkillRequest skillRequest) {
        log.trace("create skill {obj}, ", skillRequest);
        Skill skill = skillConverter.convertToEntity(skillRequest);
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, SkillRequest skillRequest) {
        log.trace("update skill {obj}, ", skillRequest);
        Skill skill = skillRepository.findById(id).orElseThrow(()-> new NotFoundException(errorCodes.getNotFound()));
        skill.setSkillName(skillRequest.getSkillName());
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        log.trace("delete skill {id}, ", id);
        skillRepository.deleteById(id);
    }
}
