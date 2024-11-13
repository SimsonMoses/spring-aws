package com.aws.service.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResponse {
    private String message;
    private HttpStatus status;
    private Object data;

}