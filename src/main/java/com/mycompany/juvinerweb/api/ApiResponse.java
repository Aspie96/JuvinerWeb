/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.api;

public class ApiResponse {
    private final boolean success;
    
    protected ApiResponse(boolean success) {
        this.success = success;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
}
