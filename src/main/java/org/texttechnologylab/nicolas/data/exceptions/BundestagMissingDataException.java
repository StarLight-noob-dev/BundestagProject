package org.texttechnologylab.nicolas.data.exceptions;

public class BundestagMissingDataException extends Exception {

    public BundestagMissingDataException(){
        super();
    }

    public BundestagMissingDataException(Throwable cause){super(cause);}

    public BundestagMissingDataException(String message){super(message);}

    public BundestagMissingDataException(String message, Throwable cause){super(message, cause);}
}
