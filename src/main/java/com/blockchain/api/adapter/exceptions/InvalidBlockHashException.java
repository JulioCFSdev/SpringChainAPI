package com.blockchain.api.adapter.exceptions;

public class InvalidBlockHashException extends RuntimeException{
    public InvalidBlockHashException() { super("BlockHash is Invalid!");}
    public InvalidBlockHashException(String message) { super(message);}
}
