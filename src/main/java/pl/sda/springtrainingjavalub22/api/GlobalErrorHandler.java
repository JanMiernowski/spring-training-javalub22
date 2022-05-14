package pl.sda.springtrainingjavalub22.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.sda.springtrainingjavalub22.api.exception.AlreadyExistException;

import java.time.LocalDateTime;
import java.util.UUID;


@ControllerAdvice
@ResponseBody
@Order(1)
public class GlobalErrorHandler {

    Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler(value = AlreadyExistException.class)
    public ResponseEntity<Error> handleAlreadyExist(AlreadyExistException ex){
        String errorCode = UUID.randomUUID().toString();
        System.out.println("Error code " + errorCode);
        ex.printStackTrace();

        return ResponseEntity.status(400).body(new Error(ex.getMessage(),LocalDateTime.now(),errorCode));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Error> handleRuntimeException(RuntimeException ex){
        String errorCode = UUID.randomUUID().toString();
        System.out.println("Error code " + errorCode);
        ex.printStackTrace();

        return ResponseEntity.status(500).body(new Error(ex.getMessage(),LocalDateTime.now(),errorCode));
    }

    @AllArgsConstructor
    @Getter
    static class Error{
        private String message;
        private LocalDateTime errorTime;
        private String errorCode;
    }
}
