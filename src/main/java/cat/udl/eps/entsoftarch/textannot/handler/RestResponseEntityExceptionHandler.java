package cat.udl.eps.entsoftarch.textannot.handler;

import lombok.Data;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends
        ResponseEntityExceptionHandler {

    @ExceptionHandler({RepositoryConstraintViolationException.class})
    public ResponseEntity<Map<String, List<ErrorInfo>>> handleRepositoryConstraintViolationException(
            Exception ex, WebRequest request) {
        RepositoryConstraintViolationException nevEx =
                (RepositoryConstraintViolationException) ex;

        List<ErrorInfo> errorInfoList = nevEx
                .getErrors()
                .getAllErrors()
                .stream()
                .map(error -> new ErrorInfo(error.getObjectName(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        Map<String, List<ErrorInfo>> result = new HashMap<>();
        result.put("errors", errorInfoList);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(result, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @Data
    public static class ErrorInfo {
        private final String entity;
        private final String message;
    }
}