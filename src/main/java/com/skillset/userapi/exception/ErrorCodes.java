package com.skillset.userapi.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class ErrorCodes {

    @NotNull
    @Valid
    @JsonProperty("not_found")
    private ErrorCodeMessage notFound;

}
