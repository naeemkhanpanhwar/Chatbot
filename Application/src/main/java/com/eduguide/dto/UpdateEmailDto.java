package com.eduguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEmailDto {
	private String email;
	private String newEmail;
}
