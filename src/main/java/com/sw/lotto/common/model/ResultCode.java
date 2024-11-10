package com.sw.lotto.common.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    SUCCESS(HttpStatus.OK),
    FAILURE();

    final HttpStatus httpStatus;

    ResultCode() {
        httpStatus = HttpStatus.OK;
    }

    ResultCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
