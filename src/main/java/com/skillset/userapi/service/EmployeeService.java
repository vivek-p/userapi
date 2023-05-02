package com.skillset.userapi.service;

import com.skillset.userapi.converter.EmployeeConverter;
import com.skillset.userapi.domain.Employee;
import com.skillset.userapi.exception.ErrorCodes;
import com.skillset.userapi.exception.NotFoundException;
import com.skillset.userapi.model.EmployeeRequest;
import com.skillset.userapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ErrorCodes errorCodes;

    private final EmployeeConverter employeeConverter;

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
        List<Employee> employeeList = employeeRepository.findByEmployeeNameLike
                ("%" + criteria + "%", pageable);

        if(includeSkills) {
            employeeList.forEach(employee -> employee.getSkillRatings());
        }

        return employeeList;
    }

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        log.trace("Create employee obj{}, ", employeeRequest);
        Employee employee = employeeConverter.convertToEntity(employeeRequest);

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeRequest employeeRequest) {
        log.trace("Update employee, request obj{}, ", employeeRequest);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(errorCodes.getNotFound()));

        if(Objects.isNull(employeeRequest) && StringUtils.hasLength(employeeRequest.getDepartment())) {
            employee.setDepartment(employeeRequest.getDepartment());
        }

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        log.trace("Deleting employee {id}", id);
        employeeRepository.deleteById(id);
    }

    public void setEmployeeSkillset() {

    }
}
