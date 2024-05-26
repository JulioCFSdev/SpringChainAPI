package com.blockchain.api.adapter.exceptions;

public class InvalidPreviousHashException extends RuntimeException{
    public InvalidPreviousHashException() { super("PreviousHash is invalid!");}
    public InvalidPreviousHashException(String message) { super(message);}
}
