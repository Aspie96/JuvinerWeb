package com.mycompany.juvinerweb.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import java.util.HashMap;
import java.util.Map;

public final class ApiFailureResponse extends ApiResponse {
    private static class Err {
        private final String description;
        
        public Err(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return this.description;
        }
    }

    private final Err error;

    public ApiFailureResponse(String descritpion) {
        super(false);
        this.error = new Err(descritpion);
    }

    public Object getError() {
        return this.error;
    }
}
