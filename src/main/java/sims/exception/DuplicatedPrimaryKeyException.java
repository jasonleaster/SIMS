package sims.exception;

public class DuplicatedPrimaryKeyException extends Exception{

	private static final long serialVersionUID = 1L;

	public DuplicatedPrimaryKeyException() {
        super("Trying to insert the object into table with duplicated primary key");
    }
}
