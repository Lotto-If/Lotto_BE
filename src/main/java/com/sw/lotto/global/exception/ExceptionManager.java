package com.sw.lotto.global.exception;

import com.sw.lotto.global.common.model.ApiResponse;
import com.sw.lotto.global.common.model.ResultCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ResultCode.FAILURE, errorMessage, null));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ResultCode.FAILURE, errorMessage, null));
    }


    // exceptionHandelr를 통해 예외 전역 관리
    @ExceptionHandler(AppException.class)
    public ApiResponse handleAppException(AppException exception) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        if (exception.getExceptionCode() != null) {
            // ExceptionCode를 사용하는 경우
            final ExceptionCode exceptionCode = exception.getExceptionCode();
            response.setStatus(exceptionCode.getResultCode().getHttpStatus().value());
            return new ApiResponse(exceptionCode.getResultCode(), exceptionCode.getResultMessage(), null);
        } else {
            // 직접 코드 ,메시지 넣은 경우
            response.setStatus(exception.getResultCode().getHttpStatus().value());
            return new ApiResponse(exception.getResultCode(), exception.getResultMessage(), null);
        }
    }
}
