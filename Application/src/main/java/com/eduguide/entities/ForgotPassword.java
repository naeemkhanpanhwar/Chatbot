package com.eduguide.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ForgotPassword {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer fpId;
	private Integer otp;
	private Date expirationTime;
	
	private String email;
}