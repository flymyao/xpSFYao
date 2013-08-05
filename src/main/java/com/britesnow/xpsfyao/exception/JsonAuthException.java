package com.britesnow.xpsfyao.exception;


public class JsonAuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JsonAuthException() {
        super("User auth fail, need login again");
    }
}
