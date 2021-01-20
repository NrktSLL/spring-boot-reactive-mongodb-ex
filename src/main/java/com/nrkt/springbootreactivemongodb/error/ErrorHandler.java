package com.nrkt.springbootreactivemongodb.error;

import com.nrkt.springbootreactivemongodb.exception.GridFsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            GridFsException.class,
    })
    @ResponseStatus(BAD_REQUEST)
    Mono<ApiError> handleBadRequestException(Exception ex, ServerHttpRequest request) {
        return errorDetails(ex.getMessage(), BAD_REQUEST, request);
    }

    @ExceptionHandler({
            Exception.class,
    })
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    Mono<ApiError> handleException(Exception ex, ServerHttpRequest request) {
        return errorDetails(ex.getMessage(), INTERNAL_SERVER_ERROR, request);
    }

    private Mono<ApiError> errorDetails(String message, HttpStatus httpStatus, ServerHttpRequest request) {
        var errorDetail = ApiError.builder()
                .message(message)
                .status(httpStatus.value())
                .timestamp(new Date())
                .error(httpStatus.getReasonPhrase())
                .path(request.getURI().toString().substring(request.getPath().toString().length())).build();

        log.error(errorDetail.getMessage());
        return Mono.just(errorDetail);
    }
}