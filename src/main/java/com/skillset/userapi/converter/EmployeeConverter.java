package com.skillset.userapi.converter;

import com.skillset.userapi.domain.Employee;
import com.skillset.userapi.model.EmployeeRequest;
import com.skillset.userapi.model.EmployeeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EmployeeConverter {

    public EmployeeResponse convertToResponse(Employee employee) {
        return EmployeeResponse.builder().id(employee.getId()).department(employee.getDepartment()).build();
    }

    public List<EmployeeResponse> toResponses(List<Employee> employeeList) {
        return employeeList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public Employee convertToEntity(EmployeeRequest employeeRequest) {
        return Employee.builder().employeeName(employeeRequest.getEmployeeName())
                .department(employeeRequest.getDepartment()).build();
    }
}
