package com.skillset.userapi.repository;

import com.skillset.userapi.domain.Employee;
import com.skillset.userapi.domain.Skill;
import com.skillset.userapi.domain.SkillRating;
import com.skillset.userapi.domain.SkillRatingKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.CollectionUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
@Slf4j
public class EmployeeRepositoryTest {

    @Container
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("test")
            .withUsername("sa")
            .withPassword("sa");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    SkillRatingRepository skillRatingRepository;

    @Autowired
    SkillRepository skillRepository;

    @BeforeEach
    public void before() {
        employeeRepository.deleteAll();
        skillRatingRepository.deleteAll();
        skillRepository.deleteAll();
    }

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

    /*@Test
    public void when_CreateEmployeeWithSkill_Expect_Success() {

    }

    @Test
    public void when_CreateEmployeeWithDuplicateName_Expect_Failure() {

    }

    @Test
    public void when_UpdateEmployee_Expect_DataToBeUpdated() {

    }

    @Test
    public void when_DeleteEmployee_Expect_UserToBeDeleted() {

    }

    @Test
    public void when_DeleteEmployeeWithInvalidId_Expect_Failure() {

    }

    @Test
    public void when_GetEmployeeWithValidId_Expect_Success() {

    }

    @Test
    public void when_GetEmployeeWithInvalidId_Expect_Failure() {

    }

    @Test
    public void when_GetEmployeeWithCriteriaSearch_Expect_Success() {

    }*/

    public Employee createEmployeeDomain(String name, String department, List<SkillRating> skillRatingList) {
        Employee.EmployeeBuilder builder =  Employee.builder().employeeName(name).department(department);
        if(!CollectionUtils.isEmpty(skillRatingList)) {
            builder.skillRatings(skillRatingList);
        }
        return builder.build();
    }

    public SkillRating createSkillRatingDomain(Long employeeId, Long skillId, int rating) {
        return SkillRating.builder().id(new SkillRatingKey(employeeId, skillId)).rating(rating).build();
    }

    public Skill createSkillDomain(String skillName) {
        return Skill.builder().skillName(skillName).build();
    }
}
