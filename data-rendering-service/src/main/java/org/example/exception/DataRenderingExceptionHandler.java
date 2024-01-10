package org.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DataRenderingExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(DataRenderingExceptionHandler.class);

    @ExceptionHandler(IncorrectRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError exceptionBadRequest(RuntimeException e) {
        String reason = "Incorrectly made request.";
        logger.error(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.toString(), reason, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(GeometryMappingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError exceptionGeometryMappingException(RuntimeException e) {
        String reason = "Geometry mapper exception.";
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), reason, e.getMessage(), LocalDateTime.now());
    }

}
