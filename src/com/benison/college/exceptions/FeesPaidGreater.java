package com.benison.college.exceptions;

public class FeesPaidGreater extends RuntimeException {
    public FeesPaidGreater(String s){
        super(s);
    }
}
