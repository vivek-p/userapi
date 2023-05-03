package com.skillset.userapi.service;

import com.skillset.userapi.converter.EmployeeConverter;
import com.skillset.userapi.domain.Employee;
import com.skillset.userapi.domain.SkillRating;
import com.skillset.userapi.exception.ErrorCodes;
import com.skillset.userapi.exception.NotFoundException;
import com.skillset.userapi.model.EmployeeRequest;
import com.skillset.userapi.model.EmployeeResponse;
import com.skillset.userapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ErrorCodes errorCodes;

    private final EmployeeConverter employeeConverter;

    private final SkillRatingService skillRatingService;

    private final SkillService skillService;

    public Employee getEmployeeById(Long id, Boolean includeSkills) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new NotFoundException(errorCodes.getNotFound()));

        if(includeSkills) {
            employee.getSkillRatings();
        }
        return employee;
    }

    public List<Employee> getEmployeesByNameLike(String criteria, Boolean includeSkills, Pageable pageable) {
        log.trace("Get employees by name like {criteria}, ", criteria);
        List<Employee> employeeList = employeeRepository.findByEmployeeNameLikeIgnoreCase
                ("%" + criteria + "%", pageable);

        if(includeSkills) {
            employeeList.forEach(employee -> employee.getSkillRatings());
        }

        return employeeList;
    }

    public List<EmployeeResponse> searchEmployeesBySkillName(String skillName, Pageable pageable) {
        log.trace("searchEmployeesBySkillName {skillName}, ", skillName);
        List<Long> skillIdList = skillService.getSkillIdsBySkillNameLike(skillName);
        return skillRatingService.searchBySkillIdsIn(skillIdList);
    }

    public List<EmployeeResponse> searchEmployeesBySkillIds(List<Long> skillIds) {
        log.trace("searchEmployeesBySkillIds {skillIds}, ", skillIds);
        return skillRatingService.searchBySkillIdsIn(skillIds);
    }

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        log.trace("Create employee obj{}, ", employeeRequest);
        Employee employee = employeeConverter.convertToEntity(employeeRequest);

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, EmployeeRequest employeeRequest) {
        log.trace("Update employee, request obj{}, ", employeeRequest);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(errorCodes.getNotFound()));
        List<SkillRating> employeeSkillRating = new ArrayList<>();

        if(!Objects.isNull(employeeRequest)) {

            if(StringUtils.hasLength(employeeRequest.getDepartment())) {
                employee.setDepartment(employeeRequest.getDepartment());
            }

            if(!CollectionUtils.isEmpty(employeeRequest.getSkillRating())) {
                employeeRequest.getSkillRating().forEach(skillRating -> {
                    employeeSkillRating.add(skillRatingService.createNewSkillRating(employee,
                            skillService.findById(skillRating.getSkillId())
                            , skillRating.getSkillRating()));
                });
                employee.setSkillRatings(employeeSkillRating);
            }
        }

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        log.trace("Deleting employee {id}", id);
        employeeRepository.deleteById(id);
    }
}
