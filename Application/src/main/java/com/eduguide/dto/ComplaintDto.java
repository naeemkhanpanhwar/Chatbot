package com.eduguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {
	private Long id;
	private String name;
	private String email;
	private String complaintDescription;
}
