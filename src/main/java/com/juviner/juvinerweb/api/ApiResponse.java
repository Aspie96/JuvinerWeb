package com.juviner.juvinerweb.api;

public class ApiResponse {
    private final boolean success;
    
    protected ApiResponse(boolean success) {
        this.success = success;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
}
