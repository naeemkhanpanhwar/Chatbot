package com.eduguide.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduguide.entities.ForgotPassword;
import com.eduguide.entities.User;
import com.eduguide.repositories.ForgotPasswordRepository;
import com.eduguide.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ForgotPasswordService {

	@Autowired
	private final UserRepository userRepository;	
	@Autowired
    private final ForgotPasswordRepository forgotPasswordRepository;
	@Autowired
    private final JavaMailSender javaMailSender;
	@Autowired
    private final PasswordEncoder passwordEncoder;

    private Integer generateOtp() {
        return new Random().nextInt(9000) + 1000; // 4-digit OTP
    }

    public String sendOtp(String email) {
        // First, check if the email already has any OTP entries
        List<ForgotPassword> existingOtpRecords = forgotPasswordRepository.findByEmail(email);

        // If there are any OTP records, delete them to avoid duplication
        if (!existingOtpRecords.isEmpty()) {
            forgotPasswordRepository.deleteAll(existingOtpRecords);
        }

        // Proceed with generating and sending a new OTP
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            int otp = generateOtp();

            ForgotPassword forgotPassword = new ForgotPassword();
            forgotPassword.setOtp(otp);
            forgotPassword.setExpirationTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 min expiry
            forgotPassword.setEmail(user.getEmail());

            forgotPasswordRepository.save(forgotPassword);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Forgot Password OTP");
            message.setText("Use this OTP: " + otp + " to reset your password. It is valid for 5 minutes.");
            javaMailSender.send(message);

            return "OTP sent to email.";
        }

        return "User with this email does not exist.";
    }
    
    
    
    public String resetPassword(String email, int otp, String newPassword, String confirmPassword) {
        // Find the ForgotPassword record by email
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepository.findByEmail(email).stream()
            .filter(fp -> fp.getOtp() == otp)  // Filter by OTP if needed
            .findFirst();

        if (forgotPasswordOptional.isPresent()) {
            ForgotPassword forgotPassword = forgotPasswordOptional.get();

            // Check if OTP is still valid (hasn't expired)
            if (forgotPassword.getExpirationTime().after(new Date())) {

                // Check if the new password matches the confirmation password
                if (newPassword.equals(confirmPassword)) {

                    // Find the user by email and update the password
                    Optional<User> userOptional = userRepository.findByEmail(email);
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        // Encode the new password
                        user.setPassword(passwordEncoder.encode(newPassword));
                        userRepository.save(user);

                        // Delete the OTP record after successful password reset
                        forgotPasswordRepository.delete(forgotPassword);

                        return "Password reset successfully.";
                    }
                    return "User not found.";
                }
                return "Passwords do not match.";
            }
            return "OTP has expired.";
        }
        return "Invalid OTP.";
    }

}
