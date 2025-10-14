package vn.edu.fpt.elios_user_service.controller.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.edu.fpt.elios_user_service.controller.api.ApiResponse;
import vn.edu.fpt.elios_user_service.domain.exception.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFound(NotFoundException ex) {
        return new ApiResponse<>(404, ex.getMessage(), null);
    }
}


