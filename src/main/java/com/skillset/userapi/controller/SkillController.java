package com.skillset.userapi.controller;

import com.skillset.userapi.converter.SkillConverter;
import com.skillset.userapi.domain.Skill_;
import com.skillset.userapi.model.SkillRequest;
import com.skillset.userapi.model.SkillResponse;
import com.skillset.userapi.service.SkillService;
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
@RequestMapping("/api/v1/skill")
@CrossOrigin(origins = "${access.cors.allowed-origins}", exposedHeaders = { "Access-Control-Allow-Origin",
        "content-disposition" }, methods = { RequestMethod.OPTIONS, RequestMethod.POST,
        RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT })
public class SkillController {

    private final SkillService skillService;

    private final SkillConverter skillConverter;

    @GetMapping("/{id}")
    @ApiOperation(value = "Return skill by Id", notes = "Api operation to return skill by id")
    public SkillResponse getById(@PathVariable(name = "id")
                                     @Parameter(description = "Skill id",required = true) Long id) {
        return skillConverter.convert(skillService.findById(id));
    }

    @GetMapping("/name/{criteria}")
    @ApiOperation(value = "Return skill by name criteria search",
            notes = "Api operation to return skill by name criteria search")
    public List<SkillResponse> getByNameLike(@PathVariable(name = "criteria")
                                                 @Parameter(description = "Skill name like", required = true)
                                                 String criteria,
                                             @PageableDefault(size = 10, sort = Skill_.ID,
                                                     direction = Sort.Direction.DESC)
                                             @Parameter(description = "Pagination Object to get groups")
                                             Pageable pageable) {
        return skillConverter.toResponses(skillService.findByNameLike(criteria, pageable));
    }

    @PostMapping
    @ApiOperation(value = "Operation to create a skill", notes = "Api operation to create a new skill")
    public SkillResponse createSkill(@RequestBody @Valid
                                         @Parameter(description = "New skill request") SkillRequest skillRequest) {
        return skillConverter.convert(skillService.createSkill(skillRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Operation to update a skill",
            notes = "Api operation to update a skill")
    public SkillResponse updateSkill(@PathVariable(name = "id", required = true)
                                         @Parameter(description = "Skill id", required = true) Long id,
                                     @RequestBody @Valid
                                     @Parameter(description = "Skill request") SkillRequest skillRequest) {
        return skillConverter.convert(skillService.updateSkill(id, skillRequest));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Operation to delete a skill", notes = "Api operation to delete a skill")
    public void deleteSkill(@PathVariable(name = "id", required = true)
                                @Parameter(description = "Skill id", required = true) Long id) {
        skillService.deleteSkill(id);
    }
}
