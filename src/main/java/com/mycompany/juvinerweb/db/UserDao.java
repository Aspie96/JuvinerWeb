/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.db;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Aspie96
 */
public interface UserDao extends CrudRepository<UserE, Integer> { }
