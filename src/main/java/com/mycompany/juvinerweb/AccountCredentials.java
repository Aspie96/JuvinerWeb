/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

public class AccountCredentials {
    private String username;
    private String password;
    private boolean header;
    
    public AccountCredentials() {
        
    }
    
    public AccountCredentials(String username, String password, boolean header) {
        this.username = username;
        this.password = password;
        this.header = header;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public boolean getHeader() {
        return this.header;
    }
}
