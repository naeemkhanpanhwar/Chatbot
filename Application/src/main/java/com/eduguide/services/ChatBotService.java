package com.eduguide.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatBotService {
	
String GEMINI_API_KEY = "AIzaSyBdCO4j1OIcUlm_UCWKkXgZV2sOmAnfFn0";
	
	
	public String simpleChat(String prompt) {
		String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + GEMINI_API_KEY;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String requestBody = new JSONObject().put("contents", new JSONArray()
				.put(new JSONObject()
						.put("parts", new JSONArray()
								.put(new JSONObject()
										.put("text", prompt))))
				).toString();
		
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.postForEntity(GEMINI_API_URL, requestEntity,String.class);
	
		return response.getBody();
	}

}
