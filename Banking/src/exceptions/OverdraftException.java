package exceptions;

public class OverdraftException extends Exception{

    public OverdraftException(String message, double deficit){
        super(message);
    }
}
