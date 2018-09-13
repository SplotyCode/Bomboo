package de.splotycode.bamboo.core.exceptions;

public class MethodNotSupportedException extends RuntimeException {

    public MethodNotSupportedException() {
    }

    public MethodNotSupportedException(String s) {
        super(s);
    }

    public MethodNotSupportedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MethodNotSupportedException(Throwable throwable) {
        super(throwable);
    }

    public MethodNotSupportedException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
