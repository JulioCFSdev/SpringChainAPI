package com.blockchain.api.exceptions;

public class InvalidBlockHashException extends RuntimeException{
    public InvalidBlockHashException() { super("BlockHash is Invalid!");}
    public InvalidBlockHashException(String message) { super(message);}
}
