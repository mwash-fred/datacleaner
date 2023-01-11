package co.wmc.datacleaner.Utils;

import co.wmc.datacleaner.DTO.CustomResponse;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<CustomResponse> handleNullPointer(
            NullPointerException ex) {
        ex.printStackTrace();
        CustomResponse response = CustomResponse.builder()
                .error(ex.getLocalizedMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<CustomResponse> handleIllegalStateException(
            IllegalStateException ex) {
        ex.printStackTrace();
        CustomResponse response = CustomResponse.builder()
                .error(ex.getLocalizedMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<CustomResponse> handleRunTime(
            RuntimeException ex) {
        ex.printStackTrace();
        CustomResponse response = CustomResponse.builder()
                .error(ex.getLocalizedMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<CustomResponse> handleRunTime(
            IOException ex) {
        ex.printStackTrace();
        CustomResponse response = CustomResponse.builder()
                .error(ex.getLocalizedMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<CustomResponse> handleExeption(
            SQLException ex) {
        ex.printStackTrace();
        CustomResponse response = CustomResponse.builder()
                .error(ex.getLocalizedMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(EncryptedDocumentException.class)
    protected ResponseEntity<CustomResponse> handleExeption(
            EncryptedDocumentException ex) {
        ex.printStackTrace();
        CustomResponse response = CustomResponse.builder()
                .error(ex.getLocalizedMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return buildResponseEntity(response);
    }

    private ResponseEntity<CustomResponse> buildResponseEntity(CustomResponse response) {
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getHttpStatus()));
    }
}
