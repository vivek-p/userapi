package com.skillset.userapi.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCodeMessage {

    @NotEmpty
    @JsonProperty("error_code")
    private String errorCode;

    @NotEmpty
    @JsonProperty("error_message")
    private String errorMessage;
}
