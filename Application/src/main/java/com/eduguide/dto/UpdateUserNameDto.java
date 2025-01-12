package com.eduguide.dto;

import lombok.Data;

@Data
public class UpdateUserNameDto {
	private String email;
	private String updateFirstName;
	private String updateLastName;

}
