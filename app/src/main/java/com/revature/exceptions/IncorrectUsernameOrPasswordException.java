package com.revature.exceptions;

public class IncorrectUsernameOrPasswordException extends Exception {

    public IncorrectUsernameOrPasswordException() { super("Could not find account with the given information"); }

    public IncorrectUsernameOrPasswordException(String message) { super(message); }

}
