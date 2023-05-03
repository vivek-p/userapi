package com.skillset.userapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillset.userapi.exception.ErrorCodes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javax.validation.*;
import java.io.IOException;
import java.util.Set;

@Configuration
public class ErrorCodesConfig {

    private static final String ERROR_CODES_JSON_FILE = "error-codes.json";

    @Bean
    public ErrorCodes errorCodes(ObjectMapper objectMapper, Validator validator) throws IOException {
        Resource resource = new ClassPathResource(ERROR_CODES_JSON_FILE);
        ErrorCodes errorCodes = objectMapper.readValue(resource.getInputStream(), ErrorCodes.class);
        Set<ConstraintViolation<ErrorCodes>> validated = validator.validate(errorCodes);
        if (!validated.isEmpty())
            throw new ConstraintViolationException(validated);
        return errorCodes;
    }
}
