package com.example.Wallet.exception;

public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(String message){
        super(message);
    }
}
