package customExceptions;

/**
 * Checks to see if the value is less than the minimumValue the user 
 * inputted
 */
public class MinimumValueException extends Exception {
    private int value = 0;
    
    public MinimumValueException(int minimumValue) {
        this.value = minimumValue;
    }

    public String getMessage(){
        return "[Error]: Value must be greater than " + value;
    }
}
    

