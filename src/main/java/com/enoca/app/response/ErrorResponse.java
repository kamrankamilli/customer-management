package com.enoca.app.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"timestamp", "status", "errorTitle", "message", "path"})
public class ErrorResponse extends Response<Object> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorTitle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  List<String> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String path;

    public ErrorResponse(HttpStatusCode statusCode, HttpHeaders headers, String errorTitle, String message, List<String> errors, String path) {
        super(statusCode, headers, message);
        this.errorTitle = errorTitle;
        this.errors = errors;
        this.path = path;
    }


    @Override
    public ResponseEntity<Object> sendResponse() {
        return ResponseEntity.status(status).headers(headers).body(this);
    }
}
