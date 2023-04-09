package com.enoca.app.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Response<T> {
    protected LocalDateTime timestamp;
    protected int status;

    @JsonIgnore
    protected HttpHeaders headers;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String message;

    public Response(HttpStatusCode statusCode,HttpHeaders headers,String message){
        this.timestamp = LocalDateTime.now();
        this.status = statusCode.value();
        this.headers = headers;
        this.message = message;
    }

    public abstract ResponseEntity<T> sendResponse();

}
