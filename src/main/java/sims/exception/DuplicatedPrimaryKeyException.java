package sims.exception;

public class DuplicatedPrimaryKeyException extends Exception{
    public DuplicatedPrimaryKeyException() {
        super("Trying to insert the object into table with duplicated primary key");
    }
}
