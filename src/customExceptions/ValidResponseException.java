package customExceptions;


public class ValidResponseException extends Exception {
    public ValidResponseException() {}

    public String getMessage(){
        return "[Error]: Invalid Input";
    }
    
}