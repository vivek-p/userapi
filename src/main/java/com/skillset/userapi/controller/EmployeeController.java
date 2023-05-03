package com.skillset.userapi.controller;

import com.skillset.userapi.converter.EmployeeConverter;
import com.skillset.userapi.domain.Employee_;
import com.skillset.userapi.domain.SkillRating;
import com.skillset.userapi.model.EmployeeRequest;
import com.skillset.userapi.model.EmployeeResponse;
import com.skillset.userapi.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
@CrossOrigin(origins = "${access.cors.allowed-origins}", exposedHeaders = { "Access-Control-Allow-Origin",
        "content-disposition" }, methods = { RequestMethod.OPTIONS, RequestMethod.POST,
        RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT })
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeConverter employeeConverter;

    @GetMapping("/{id}")
    @ApiOperation(value = "Return employee by Id", notes = "Api operation to return employee by id")
    public EmployeeResponse getById(@PathVariable(name = "id")
                                        @Parameter(description = "Employee id",required = true) Long id,
                                    @RequestParam(name = "includeSkills", required = false, defaultValue = "FALSE")
                                        @Parameter(description = "Parameter to include skills", required = false)
                                    Boolean includeSkills) {
        return employeeConverter.convertToResponse(employeeService.getEmployeeById(id, includeSkills));
    }

    @GetMapping("/name/{criteria}")
    @ApiOperation(value = "Return employee by name criteria search",
            notes = "Api operation to return employee by name criteria search")
    public List<EmployeeResponse> getByNameLike(@PathVariable(name = "criteria")
                                                    @Parameter(description = "Employee name like", required = true)
                                                    String criteria,
                                                @RequestParam (name = "includeSkills", required = false,
                                                        defaultValue = "FALSE")
                                                @Parameter(description = "Parameter to include skills", required = false)
                                                Boolean includeSkills,
                                                @PageableDefault(size = 10, sort = Employee_.ID,
                                                        direction = Sort.Direction.DESC)
                                                    @Parameter(description = "Pagination Object to get employees")
                                                    Pageable pageable) {
        return employeeConverter.toResponses(employeeService.getEmployeesByNameLike(criteria, includeSkills, pageable));
    }

    @GetMapping("/searchBySkillName/{skillNameLike}")
    @ApiOperation(value = "Operation to get employees by matching skill name(Like search)",
            notes = "Api operation to search employees by skill name(Like search)")
    public List<EmployeeResponse> searchBySkillName(@PathVariable(name = "skillNameLike")
                                      @Parameter(description = "Skill name like", required = true)
                                      String skillNameLike, @PageableDefault(size = 10, sort = Employee_.ID,
                                        direction = Sort.Direction.DESC)
                                  @Parameter(description = "Pagination Object to get employees") Pageable pageable) {
        return employeeService.searchEmployeesBySkillName(skillNameLike, pageable);
    }

    @GetMapping("/searchBySkillIds/{skillIds}")
    @ApiOperation(value = "Operation to get employees by matching skill ids",
            notes = "Api operation to search employees by skill ids")
    public List<EmployeeResponse> searchBySkillIds(@PathVariable(name = "skillIds")
                                                       @Parameter(description = "Skill ids", required = true)
                                                       List<Long> skillIds) {
        return employeeService.searchEmployeesBySkillIds(skillIds);
    }

    @PostMapping
    @ApiOperation(value = "Operation to create an employee", notes = "Api operation to create a new employee")
    public EmployeeResponse createEmployee(@RequestBody @Valid
                                               @Parameter(description = "New employee request")
                                               EmployeeRequest employeeRequest) {
        return employeeConverter.convertToResponse(employeeService.createEmployee(employeeRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Operation to update an employee",
            notes = "Api operation to update an employee")
    public EmployeeResponse updateEmployee(@PathVariable(name = "id", required = true)
                                               @Parameter(description = "Employee id", required = true) Long id,
                                           @RequestBody @Valid
                                           @Parameter(description = "Employee update request object")
                                           EmployeeRequest employeeRequest) {
        return employeeConverter.convertToResponse(employeeService.updateEmployee(id, employeeRequest));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Operation to delete an employee", notes = "Api operation to delete an employee")
    public void deleteEmployee(@PathVariable(name = "id", required = true)
                                   @Parameter(description = "Employee id", required = true) Long id) {
        employeeService.deleteEmployee(id);
    }
}
