package com.eduguide.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserNameResponse {
	private String message;
	 private boolean success;
}
