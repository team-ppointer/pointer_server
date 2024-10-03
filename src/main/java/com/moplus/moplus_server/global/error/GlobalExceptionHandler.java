package com.moplus.moplus_server.global.error;

import com.moplus.moplus_server.global.error.exception.BusinessException;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception) {
        log.error("handleBusinessException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ResponseEntity<>("존재하지 않은 요청 엔드포인트입니다", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(final NotFoundException exception) {
        log.error("handleEntityNotFoundException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponse response = ErrorResponse.from(exception.getErrorCode());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        final ErrorResponse response = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
