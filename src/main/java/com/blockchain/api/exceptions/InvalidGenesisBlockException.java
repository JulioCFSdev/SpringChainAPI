package com.blockchain.api.exceptions;

public class InvalidGenesisBlockException extends RuntimeException{
    public InvalidGenesisBlockException() { super("Genesis Block is invalid!");}
    public InvalidGenesisBlockException(String message) { super(message);}
}
