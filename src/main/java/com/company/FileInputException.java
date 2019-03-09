package com.company;

public class FileInputException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String msg;

    public FileInputException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
