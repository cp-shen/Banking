package banking.domain;

public class OverdraftException extends Exception{
    private double deficit = 0;

    public double getDeficit() {
        return deficit;
    }
    OverdraftException(String message, double deficit){
        super(message);
        this.deficit = deficit;
    }
}
