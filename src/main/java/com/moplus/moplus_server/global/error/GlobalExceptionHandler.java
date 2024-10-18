package com.moplus.moplus_server.global.error;

import com.moplus.moplus_server.global.error.exception.BusinessException;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
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

    // 트랜잭션 생성 불가 예외 처리
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<String> handleCannotCreateTransactionException(CannotCreateTransactionException ex) {
        log.error("handleEntityNotFoundException", ex);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("요청이 많아 서비스가 지연되고 있습니다. 잠시 후 다시 시도해주세요.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        final ErrorResponse response = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
