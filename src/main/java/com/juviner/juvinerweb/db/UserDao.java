package com.juviner.juvinerweb.db;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserE, Integer> {
    public Optional<UserE> findByUsername(String username);
}
