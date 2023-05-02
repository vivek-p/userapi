package com.skillset.userapi.service;

import com.skillset.userapi.converter.SkillConverter;
import com.skillset.userapi.domain.Skill;
import com.skillset.userapi.exception.ErrorCodes;
import com.skillset.userapi.exception.NotFoundException;
import com.skillset.userapi.model.SkillRequest;
import com.skillset.userapi.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillService {

    private final SkillRepository skillRepository;

    private final ErrorCodes errorCodes;

    private final SkillConverter skillConverter;

    public Skill findById(Long id) {
        log.trace("find by {id}, ", id);
        return skillRepository.findById(id).orElseThrow(() -> new NotFoundException(errorCodes.getNotFound()));
    }

    public List<Skill> findByNameLike(String criteria, Pageable pageable) {
        log.trace("find by name like {criteria}, ", criteria);
        return skillRepository.findBySkillNameLike("%" + criteria + "%", pageable);
    }

    public Skill createSkill(SkillRequest skillRequest) {
        log.trace("create skill {obj}, ", skillRequest);
        Skill skill = skillConverter.convertToEntity(skillRequest);
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill skillRequest) {
        log.trace("update skill {obj}, ", skillRequest);
        Skill skill = skillRepository.findById(id).orElseThrow(()-> new NotFoundException(errorCodes.getNotFound()));
        skill.setSkillName(skill.getSkillName());
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        log.trace("delete skill {id}, ", id);
        skillRepository.deleteById(id);
    }
}
