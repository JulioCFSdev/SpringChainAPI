package com.blockchain.api.adapter.exceptions;

public class InvalidBlockIndexException extends Exception{
    public InvalidBlockIndexException() { super("Block Index is Invalid!");}
    public InvalidBlockIndexException(String message) { super(message);}
}
