package com.sw.lotto.global.common.controller;

import com.sw.lotto.global.common.model.ApiResponse;
import com.sw.lotto.global.common.model.PageResponse;
import com.sw.lotto.global.common.model.ResultCode;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController {
    public ApiResponse success() {
        return new ApiResponse<>(ResultCode.SUCCESS, null, null);
    }

    public <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(ResultCode.SUCCESS, null, data);
    }

    public <T> ApiResponse<T> successWithMessage(String message, T data) {
        return new ApiResponse<T>(ResultCode.SUCCESS, message, data);
    }

//    public <T> ApiResponse<List<T>> successWithPagenation(PageResponse<T> pageResponse) {
//        return new ApiResponse<>(ResultCode.SUCCESS, null,
//                pageResponse.getData(), pageResponse.getTotalItemCount() == null ? null : Math.toIntExact(pageResponse.getTotalItemCount()), pageResponse.getPageNumber());
//    }

    public <T> ApiResponse<T> failure(String message) {
        return new ApiResponse(ResultCode.FAILURE, message, null);
    }

    public <T> ApiResponse<T> response(T data, ResultCode resultCode) {
        return new ApiResponse<T>(resultCode, null, data);
    }

    public <T> ApiResponse<T> response(ResultCode resultCode) {
        return new ApiResponse<T>(resultCode, null, null);
    }
}
