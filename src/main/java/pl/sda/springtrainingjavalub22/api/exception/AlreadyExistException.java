package pl.sda.springtrainingjavalub22.api.exception;

public class AlreadyExistException extends RuntimeException{

    public AlreadyExistException(String message){
        super(message);
    }
}
