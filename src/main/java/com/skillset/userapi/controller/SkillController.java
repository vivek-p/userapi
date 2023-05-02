package com.skillset.userapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/skill")
@CrossOrigin(origins = "${access.cors.allowed-origins}", exposedHeaders = { "Access-Control-Allow-Origin",
        "content-disposition" }, methods = { RequestMethod.OPTIONS, RequestMethod.POST,
        RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT })
public class SkillController {
}
