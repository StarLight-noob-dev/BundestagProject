package org.texttechnologylab.nicolas.data.exceptions;

public class StammDatenParserException extends Exception {

    public StammDatenParserException() {
    }

    public StammDatenParserException(Throwable pCause) {
        super(pCause);
    }

    public StammDatenParserException(String pMessage) {
        super(pMessage);
    }

    public StammDatenParserException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}
