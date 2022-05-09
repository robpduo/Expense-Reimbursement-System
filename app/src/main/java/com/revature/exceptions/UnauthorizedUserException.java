package com.revature.exceptions;

public class UnauthorizedUserException extends Exception {

    public UnauthorizedUserException() { super("Only a manager can perform these actions"); }

    public UnauthorizedUserException(String message) { super(message); }

}
