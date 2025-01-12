package com.eduguide.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.eduguide.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findOneByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    
}
