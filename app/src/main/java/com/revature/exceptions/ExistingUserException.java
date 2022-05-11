package com.revature.exceptions;

public class ExistingUserException extends Exception {

    public ExistingUserException() { super("User already exists"); }

    public ExistingUserException(String message) { super(message); }

}
