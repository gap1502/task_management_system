package ru.rtischev.task_system.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "The username cannot be empty")
    private String username;


    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Incorrect email format")
    private String email;


    @NotBlank(message = "The password cannot be empty")
    @Size(min = 5, message = "The password must contain at least 5 characters")
    private String password;

}
