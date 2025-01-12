package com.eduguide.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduguide.dto.ChangePasswordDto;
import com.eduguide.dto.ComplaintDto;
import com.eduguide.dto.LoginDto;
import com.eduguide.dto.RegisterDto;
import com.eduguide.dto.UpdateEmailDto;
import com.eduguide.dto.UpdateUserNameDto;
import com.eduguide.entities.Complaint;
import com.eduguide.entities.User;
import com.eduguide.repositories.ComplaintRepository;
import com.eduguide.repositories.UserRepository;
import com.eduguide.responses.ChangePasswordResponse;
import com.eduguide.responses.LoginResponse;
import com.eduguide.responses.RegistrationResponse;
import com.eduguide.responses.UpdateEmailResponse;
import com.eduguide.responses.UpdateUserNameResponse;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ComplaintRepository complaintRepository;
	
	
		public RegistrationResponse addUser(RegisterDto registerDto) {
		
			User user = new User(
					registerDto.getId(), 
					registerDto.getFirstName(),
					registerDto.getLastName(),
					registerDto.getEmail(),
					registerDto.getPassword(),
					registerDto.getConfirmPassword()
			);
			
		
			Optional<User> storedUser = userRepository.findByEmail(registerDto.getEmail());
			if (!storedUser.isPresent()) {
		    	if(user.getPassword().equals(user.getConfirmPassword())) {
					
					//if password matches, encode and save user
				    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
				    user.setConfirmPassword(null); 
					userRepository.save(user);
					return new RegistrationResponse("Registration Successful", true);
				}
		
				else {
					 return new RegistrationResponse("Passwords do not match", false);
				}
			 }	
		     else {
		    	return new RegistrationResponse("Email already exists", false);
		     }	

		}


	
		public LoginResponse loginUser(LoginDto loginDTO) {
		    // Fetch the user by email
		    Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

		    // Check if user exists
		    if (userOptional.isPresent()) {
		        User user = userOptional.get(); // Get the User object from Optional

		        // Check if the password matches (raw password vs. encoded password)
		        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
		        	
		        	//getting username of the logged in user
		        	String username = user.getFirstName().concat(" " + user.getLastName());
		        	
//		        	System.out.println("User Name is : " + username);
		        
		            return new LoginResponse("Login Successfully", true);
		        } else {
		            return new LoginResponse("Incorrect Password", false);
		        }
		    } else {
		        // If user doesn't exist
		        return new LoginResponse("Email does not exist", false);
		    }
		}

		
		
		public UpdateEmailResponse updateEmail(UpdateEmailDto updateEmailDto) {
		Optional<User> userOptional = userRepository.findByEmail(updateEmailDto.getEmail());

		    if (userOptional.isPresent()) {
		        User user = userOptional.get();
	
		        // Check if the new email is the same as the old one
		        if (user.getEmail().equals(updateEmailDto.getNewEmail())) {
		            return new UpdateEmailResponse("New email is the same as the current email", false);
		        }
	
		        // Check if the new email is already in use
		        if (userRepository.findByEmail(updateEmailDto.getNewEmail()).isPresent()) {
		            return new UpdateEmailResponse("The new email is already in use by another user", false);
		        }
	
		        // Update email
		        user.setEmail(updateEmailDto.getNewEmail());
		        userRepository.save(user);
	
		        return new UpdateEmailResponse("Email changed successfully", true);
		    } else {
		        return new UpdateEmailResponse("The current email does not exist", false);
		    }
		}
		
		public UpdateUserNameResponse updateUserName(UpdateUserNameDto updateUserNameDto) {
		    Optional<User> userOptional = userRepository.findByEmail(updateUserNameDto.getEmail());

		    if (userOptional.isPresent()) {
		        User user = userOptional.get();

		        if (user.getFirstName().equals(updateUserNameDto.getUpdateFirstName()) && 
		            user.getLastName().equals(updateUserNameDto.getUpdateLastName())) {
		            return new UpdateUserNameResponse("New Name and Old Name cannot be the same", false);
		        } else {
		            user.setFirstName(updateUserNameDto.getUpdateFirstName());
		            user.setLastName(updateUserNameDto.getUpdateLastName());

		            userRepository.save(user);
		            return new UpdateUserNameResponse("User Name changed successfully", true);
		        }
		    } else {
		        return new UpdateUserNameResponse("Email does not exist", false);
		    }
		}

		
		public ChangePasswordResponse changePassword(ChangePasswordDto changePasswordDto) {
				
				Optional<User> userOptional = userRepository.findByEmail(changePasswordDto.getEmail());
	
			    // Check if the user exists
			    if (userOptional.isPresent()) {
			        
			        User user = userOptional.get();
			        
			        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
				        
				        if (changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
				        	
				        	user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
						    userRepository.save(user);
						    
				        	return new ChangePasswordResponse("Password changed successfully", true);
					    } else {
					    	return new ChangePasswordResponse("Passwords do not match", false);
					    }
				    } else {
				    	 return new ChangePasswordResponse("Old password is incorrect", false);
				    }    
			        
			    }
				return new ChangePasswordResponse("Email does not exists", false);
			       
			}

		
		
		public String addComplaint(ComplaintDto complaintDto) {
			
			Complaint complaint = new Complaint(
					complaintDto.getId(),
					complaintDto.getName(),
					complaintDto.getEmail(),
					complaintDto.getComplaintDescription()
					);
			
			complaintRepository.save(complaint);
			
			return "Complaint Received Successfully";
		}
		
		
	}

		
