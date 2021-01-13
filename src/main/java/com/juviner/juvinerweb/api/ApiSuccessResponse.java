package com.juviner.juvinerweb.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import java.util.HashMap;
import java.util.Map;

public final class ApiSuccessResponse extends ApiResponse {
    private static class ResponseData {
        private final Map<String, Object> properties;
        
        public ResponseData() {
            this.properties = new HashMap<>();
        }
        
        @JsonAnyGetter
        public Map<String, Object> getProperties() {
            return this.properties;
        }
    }

    private final ResponseData data;

    public ApiSuccessResponse(String name, Object data) {
        super(true);
        this.data = new ResponseData();
        this.data.getProperties().put(name, data);
    }

    @JsonGetter("data")
    public Object getData() {
        return this.data;
    }
}
