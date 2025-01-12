package com.eduguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduguide.dto.PromptBody;
import com.eduguide.services.ChatBotService;

@RestController
@CrossOrigin(origins = "*")	
@RequestMapping("/api")
public class ChatBotController {
	
	@Autowired
	private ChatBotService chatBotService;
	
	@PostMapping("/chatbot")
	public ResponseEntity<String> simpleChatHandler(@RequestBody PromptBody prompt) throws Exception{
		String response = chatBotService.simpleChat(prompt.getPrompt());
	
		return new ResponseEntity<>(response , HttpStatus.OK);
	
	}
	
	 @GetMapping("/rediectUri")
	 public String chatbotPage() {
	    return "Welcome to the Chatbot!";
	 }
	
}
