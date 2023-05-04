package com.skillset.userapi.repository;

import com.skillset.userapi.UserapiApplicationTests;
import com.skillset.userapi.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
@Slf4j
class UserApiRepositoryTest extends UserapiApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRatingRepository skillRatingRepository;

    @Autowired
    private SkillRepository skillRepository;

    @BeforeEach
    public void before() {
        employeeRepository.deleteAll();
        skillRatingRepository.deleteAll();
        skillRepository.deleteAll();
    }

    /**
     * Employee repository tests
     */
    @Test
    public void when_CreateEmployeeWithoutSkill_Expect_Success() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Employee emp2 = createEmployeeDomain("User2", "IT", null);
        emp2 = employeeRepository.save(emp2);
        Assertions.assertNotNull(emp1.getId());
        Assertions.assertNotNull(emp2.getId());
        Assertions.assertEquals(2, employeeRepository.count());
    }

    @Test
    public void when_CreateEmployeeWithDuplicateName_Expect_Failure() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Employee emp2 = createEmployeeDomain("User1", "IT", null);
        Assertions.assertNotNull(emp1.getId());
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> employeeRepository.save(emp2));
    }

    @Test
    @Transactional
    public void when_UpdateEmployee_Expect_DataToBeUpdated() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        Skill skill = createSkillDomain("Spring Boot");
        skill = skillRepository.save(skill);
        Long skillId = skill.getId();
        Assertions.assertNotNull(skillId);
        SkillRating skillRating = createSkillRatingDomain(3, emp1, skill);
        skillRating = skillRatingRepository.save(skillRating);
        Employee emp = employeeRepository.getById(empId);
        emp.setSkillRatings(Arrays.asList(skillRating));
        emp.setDepartment("Web");
        emp = employeeRepository.save(emp);
        emp.getSkillRatings();
        Assertions.assertEquals("Web", emp.getDepartment());
        Assertions.assertTrue(emp.getSkillRatings().size() == 1);
    }

    @Test
    public void when_DeleteEmployee_Expect_UserToBeDeleted() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        employeeRepository.deleteById(empId);
        Assertions.assertTrue(employeeRepository.count() == 0);
    }

    @Test
    public void when_DeleteEmployeeWithInvalidId_Expect_Failure() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> employeeRepository.deleteById(10L));
    }

    @Test
    public void when_GetEmployeeWithValidId_Expect_Success() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        Employee newEmp = employeeRepository.getById(empId);
        Assertions.assertNotNull(newEmp);
        Assertions.assertEquals(empId, newEmp.getId());
    }

    @Test
    public void when_GetEmployeeWithCriteriaSearch_Expect_Success() {
        employeeRepository.save(createEmployeeDomain("User 1", "IT", null));
        employeeRepository.save(createEmployeeDomain("User 2", "IT", null));
        employeeRepository.save(createEmployeeDomain("Test", "IT", null));
        Pageable pageable = PageRequest.of(0, 5);
        List<Employee> empList = employeeRepository.findByEmployeeNameLikeIgnoreCase("%user%", pageable);
        Assertions.assertTrue(empList.size() == 2);
    }

    /**
     * Skill repository tests
     */

    @Test
    public void when_CreateSkill_Expect_Success() {
        Skill skill = createSkillDomain("Test");
        skill = skillRepository.save(skill);
        Assertions.assertNotNull(skill);
        Assertions.assertNotNull(skill.getId());
    }

    @Test
    public void when_CreateSkillWithDuplicateName_Expect_Failure() {
        Skill skill = createSkillDomain("Test");
        skill = skillRepository.save(skill);
        Assertions.assertNotNull(skill.getId());
        Skill newSkill = createSkillDomain("Test");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> skillRepository.save(newSkill));
    }

    @Test
    public void when_UpdateSkill_Expect_Success() {
        Skill skill = createSkillDomain("Test");
        skill = skillRepository.save(skill);
        Assertions.assertNotNull(skill.getId());
        skill.setSkillName("Spring Boot");
        skill = skillRepository.save(skill);
        Assertions.assertEquals("Spring Boot", skill.getSkillName());
    }

    @Test
    public void when_DeleteSkill_Expect_Success() {
        Skill skill = createSkillDomain("Test");
        skill = skillRepository.save(skill);
        Assertions.assertNotNull(skill.getId());
        skillRepository.deleteById(skill.getId());
        Assertions.assertEquals(0, skillRepository.count());
    }

    @Test
    public void when_DeleteSkillWithWrongId_Expect_Failure() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> employeeRepository.deleteById(10L));
    }

    @Test
    public void when_SearchSkillByNameLike_Expect_Success() {
        Skill skill = createSkillDomain("Test");
        skillRepository.save(skill);
        Skill skill2 = createSkillDomain("Spring");
        skillRepository.save(skill2);
        Skill skill3 = createSkillDomain("Spring Boot");
        skillRepository.save(skill3);
        List<Skill> skills = skillRepository.findBySkillNameLikeIgnoreCase
                ("%Spring%", PageRequest.of(0, 5));
        Assertions.assertEquals(2, skills.size());
    }

    @Test
    public void when_SearchSkillByInvalidNameLike_Expect_Failure() {
        Skill skill = createSkillDomain("Test");
        skillRepository.save(skill);
        Skill skill2 = createSkillDomain("Spring");
        skillRepository.save(skill2);
        Skill skill3 = createSkillDomain("Spring Boot");
        skillRepository.save(skill3);
        List<Skill> skills = skillRepository.findBySkillNameLikeIgnoreCase
                ("%pste%", PageRequest.of(0, 5));
        Assertions.assertEquals(0, skills.size());
    }

    @Test
    public void when_GetSkillIdsBySkillNameLike_Expect_Success() {
        Skill skill = createSkillDomain("Test");
        skillRepository.save(skill);
        Skill skill2 = createSkillDomain("Spring");
        skillRepository.save(skill2);
        Skill skill3 = createSkillDomain("Spring Boot");
        skillRepository.save(skill3);
        List<Long> ids = skillRepository.findIdsBySkillNameLike("spring");
        Assertions.assertEquals(ids.size(), 2);
    }

    @Test
    public void when_GetSkillIdsBySkillNameLike_Expect_Failure() {
        Skill skill = createSkillDomain("Test");
        skillRepository.save(skill);
        Skill skill2 = createSkillDomain("Spring");
        skillRepository.save(skill2);
        Skill skill3 = createSkillDomain("Spring Boot");
        skillRepository.save(skill3);
        List<Long> ids = skillRepository.findIdsBySkillNameLike("pstt");
        Assertions.assertEquals(ids.size(), 0);
    }

    /**
     * Skill Rating Repository tests
     */
    @Test
    public void when_CreateSkillRating_Expect_Success() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        Skill skill = createSkillDomain("Spring Boot");
        skill = skillRepository.save(skill);
        Long skillId = skill.getId();
        Assertions.assertNotNull(skillId);
        SkillRating skillRating = createSkillRatingDomain(3, emp1, skill);
        skillRating = skillRatingRepository.save(skillRating);
        Assertions.assertNotNull(skillRating);
        Assertions.assertNotNull(skillRating.getId());
    }

    @Test
    public void when_UpdateSkillRating_Expect_Success() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        Skill skill = createSkillDomain("Spring Boot");
        skill = skillRepository.save(skill);
        Long skillId = skill.getId();
        Assertions.assertNotNull(skillId);
        SkillRating skillRating = createSkillRatingDomain(3, emp1, skill);
        skillRating = skillRatingRepository.save(skillRating);
        Assertions.assertNotNull(skillRating);
        Assertions.assertNotNull(skillRating.getId());
        SkillRating update = createSkillRatingDomain(1, emp1, skill);
        update = skillRatingRepository.save(update);
        Assertions.assertNotNull(update);
        Assertions.assertEquals(1, skillRatingRepository.count());
    }

    @Test
    public void when_DeleteSkillRating_Expect_Success() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        Skill skill = createSkillDomain("Spring Boot");
        skill = skillRepository.save(skill);
        Long skillId = skill.getId();
        Assertions.assertNotNull(skillId);
        SkillRating skillRating = createSkillRatingDomain(3, emp1, skill);
        skillRating = skillRatingRepository.save(skillRating);
        Assertions.assertNotNull(skillRating);
        Assertions.assertNotNull(skillRating.getId());
        Assertions.assertEquals(1, skillRatingRepository.count());
        skillRatingRepository.deleteById(new SkillRatingKey(empId, skillId));
        Assertions.assertEquals(0, skillRatingRepository.count());
    }

    @Test
    public void when_DeleteSkillRatingWithoutValidId_Expect_Failure() {
        Assertions.assertThrows(EmptyResultDataAccessException.class,
                () -> skillRatingRepository.deleteById(new SkillRatingKey(1L, 1L)));
    }

    @Test
    public void when_FindByValidEmployeeId_Expect_Success() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        Skill skill = createSkillDomain("Spring Boot");
        skill = skillRepository.save(skill);
        Long skillId = skill.getId();
        Assertions.assertNotNull(skillId);
        SkillRating skillRating = createSkillRatingDomain(3, emp1, skill);
        skillRating = skillRatingRepository.save(skillRating);
        Assertions.assertNotNull(skillRating);
        Assertions.assertNotNull(skillRating.getId());
        Assertions.assertEquals(1, skillRatingRepository.count());
        List<SkillRating> searchList = skillRatingRepository.findByIdEmployeeId(empId);
        Assertions.assertNotNull(skillRating);
        Assertions.assertEquals(1, searchList.size());
    }

    @Test
    public void when_FindByInvalidEmployeeId_Expect_Failure() {
        Assertions.assertThrows(EmptyResultDataAccessException.class,
                () -> skillRatingRepository.findByIdEmployeeId(1L));
    }

    @Test
    public void when_FindByValidSkillId_Expect_Success() {
        Employee emp1 = createEmployeeDomain("User1", "IT", null);
        emp1 = employeeRepository.save(emp1);
        Long empId = emp1.getId();
        Assertions.assertNotNull(empId);
        Skill skill = createSkillDomain("Spring Boot");
        skill = skillRepository.save(skill);
        Long skillId = skill.getId();
        Assertions.assertNotNull(skillId);
        SkillRating skillRating = createSkillRatingDomain(3, emp1, skill);
        skillRating = skillRatingRepository.save(skillRating);
        Assertions.assertNotNull(skillRating);
        Assertions.assertNotNull(skillRating.getId());
        Assertions.assertEquals(1, skillRatingRepository.count());
        List<SkillRating> searchList = skillRatingRepository.findByIdSkillId(skillId);
        Assertions.assertNotNull(skillRating);
        Assertions.assertEquals(1, searchList.size());
    }

    @Test
    public void when_FindByInvalidSkillId_Expect_Failure() {
        Assertions.assertThrows(EmptyResultDataAccessException.class,
                () -> skillRatingRepository.findByIdSkillId(1L));
    }

    public Employee createEmployeeDomain(String name, String department, List<SkillRating> skillRatingList) {
        Employee.EmployeeBuilder builder =  Employee.builder().employeeName(name).department(department);
        if(!CollectionUtils.isEmpty(skillRatingList)) {
            builder.skillRatings(skillRatingList);
        }
        return builder.build();
    }

    public SkillRating createSkillRatingDomain(int rating, Employee employee, Skill skill) {
        return SkillRating.builder().id(new SkillRatingKey(employee.getId(), skill.getId())).rating(rating)
                .employee(employee).skill(skill).build();
    }

    public Skill createSkillDomain(String skillName) {
        return Skill.builder().skillName(skillName).build();
    }
}
