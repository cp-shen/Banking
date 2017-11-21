package exceptions;

public class ConflictedIdException extends Exception{
    public ConflictedIdException(){
        super("ID conflicted");
    }
}
