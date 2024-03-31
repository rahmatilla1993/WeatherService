package com.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterDto {
    @NotBlank(message = "Firstname can't be blank")
    String firstname;
    @NotBlank(message = "Lastname can't be blank")
    String lastname;
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Did not match email pattern")
    String email;
    @NotBlank(message = "Password can't be blank")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$",
            message = "Minimum four characters, at least one letter and one number"
    )
    String password;
}
