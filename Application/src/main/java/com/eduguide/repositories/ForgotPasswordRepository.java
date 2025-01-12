package com.eduguide.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduguide.entities.ForgotPassword;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
		
	// Optional<ForgotPassword> findByEmail(String email);
	 List<ForgotPassword> findByEmail(String email);
}
