package com.skillset.userapi.service;

import com.skillset.userapi.converter.SkillRatingConverter;
import com.skillset.userapi.domain.Employee;
import com.skillset.userapi.domain.Skill;
import com.skillset.userapi.domain.SkillRating;
import com.skillset.userapi.domain.SkillRatingKey;
import com.skillset.userapi.enums.SkillRatingEnum;
import com.skillset.userapi.exception.ErrorCodes;
import com.skillset.userapi.exception.GenericException;
import com.skillset.userapi.model.EmployeeResponse;
import com.skillset.userapi.model.SearchResponse;
import com.skillset.userapi.model.SkillRatingResponse;
import com.skillset.userapi.repository.SkillRatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillRatingService {

    private final SkillRatingRepository skillRatingRepository;

    private final ErrorCodes errorCodes;

    private final SkillRatingConverter skillRatingConverter;

    public List<SkillRating> findByEmployeeId(Long employeeId) {
        log.trace("findByEmployeeId {employeeId} ", employeeId);
        return skillRatingRepository.findByIdEmployeeId(employeeId);
    }

    public List<SkillRating> findBySkillId(Long skillId) {
        log.trace("findBySkillId {skillId}", skillId);
        return skillRatingRepository.findByIdSkillId(skillId);
    }

    public List<EmployeeResponse> searchBySkillIdsIn(List<Long> skillIds) {
        List<EmployeeResponse> employeeResponse = new ArrayList<>();
        List<SearchResponse> response = skillRatingRepository.findDetailsBySkillIdsIn(skillIds);
        if(!CollectionUtils.isEmpty(response)) {
            Set<Long> uniqueEmployeeIds =  response.stream()
                    .map(item -> item.getEmployeeId()).collect(Collectors.toSet());
            uniqueEmployeeIds.stream().forEach(id -> {
                List<SearchResponse> byEmpId = response.stream().filter(item -> item.getEmployeeId() == id)
                        .collect(Collectors.toList());
                SearchResponse searchedEmployee = byEmpId.get(0);
                employeeResponse.add(EmployeeResponse.builder().employeeName(searchedEmployee.getEmployeeName())
                        .department(searchedEmployee.getDepartment())
                        .id(searchedEmployee.getEmployeeId()).skillset(collectSkills(byEmpId)).build());
            });
        }

        return employeeResponse;
    }

    private List<SkillRatingResponse> collectSkills(List<SearchResponse> list) {
        return skillRatingConverter.convertSearchResultToResponse(list);
    }

    public SkillRating createNewSkillRating(Employee employee, Skill skill, Integer rating) {
        log.trace("createNewSkillRating employee {employee}, skill {skill}, rating {rating}", employee, skill, rating);
        SkillRatingKey key = new SkillRatingKey(employee.getId(), skill.getId());
        SkillRatingEnum skillRatingEnum = SkillRatingEnum.getSkillRatingType(rating);
        if(Objects.isNull(skillRatingEnum)) {
            throw new GenericException(errorCodes.getInvalidSkillRating());
        }
        SkillRating skillRating = SkillRating.builder().id(key).rating(skillRatingEnum.getCode()).skill(skill)
                .employee(employee).build();
        return skillRatingRepository.save(skillRating);
    }
}
