package edu.eci.arsw.api.primesrepo.model;

public class PrimeException extends Exception {

    public PrimeException(String msg){
        super(msg);
    }

    public PrimeException(String msg, Throwable throwable){
        super(msg,throwable);
    }
}
