package com.eduguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eduguide.dto.ChangePasswordDto;
import com.eduguide.dto.ComplaintDto;
import com.eduguide.dto.ForgotPasswordDto;
import com.eduguide.dto.LoginDto;
import com.eduguide.dto.RegisterDto;
import com.eduguide.dto.UpdateEmailDto;
import com.eduguide.dto.UpdateUserNameDto;
import com.eduguide.responses.ChangePasswordResponse;
import com.eduguide.responses.LoginResponse;
import com.eduguide.responses.RegistrationResponse;
import com.eduguide.responses.UpdateEmailResponse;
import com.eduguide.responses.UpdateUserNameResponse;
import com.eduguide.services.ForgotPasswordService;
import com.eduguide.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "*")			//to connect with front end
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
	
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private final ForgotPasswordService forgotPasswordService;
	
	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegisterDto userDto) {
		RegistrationResponse registerResponse = userService.addUser(userDto);
		return ResponseEntity.ok(registerResponse);
	
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginDto loginDTO){
		
		LoginResponse loginResponse = userService.loginUser(loginDTO);
		
		return ResponseEntity.ok(loginResponse);
	
	}
	
	@PostMapping("/updateEmail")
    public ResponseEntity<UpdateEmailResponse> updateEmail(@RequestBody UpdateEmailDto updateEmailDto) {
        // Call the service method to change the password
		UpdateEmailResponse response = userService.updateEmail(updateEmailDto);

        return ResponseEntity.ok(response);
    }
	
	@PostMapping("/updateUserName")
    public ResponseEntity<UpdateUserNameResponse> updateUserName(@RequestBody UpdateUserNameDto updateUserNameDto ) {
        // Call the service method to change the password
		UpdateUserNameResponse response = userService.updateUserName(updateUserNameDto);

        return ResponseEntity.ok(response);
    }
	
	@PostMapping("/updatePassword")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        // Call the service method to change the password
		ChangePasswordResponse response = userService.changePassword(changePasswordDto);

        return ResponseEntity.ok(response);
    }
	

    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        String response = forgotPasswordService.sendOtp(forgotPasswordDto.getEmail());
            return ResponseEntity.ok(response);       
    }
	
    
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam int otp,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {
        String response = forgotPasswordService.resetPassword(email, otp, newPassword, confirmPassword);
        return ResponseEntity.ok(response);
    }
    
    
    @PostMapping("/complaint")
	public ResponseEntity<String> addComplaint(@RequestBody ComplaintDto complaintDto){
		String response = userService.addComplaint(complaintDto);
		return ResponseEntity.ok(response);
	}
	
}