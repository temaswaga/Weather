package model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class SignUpDto {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    private String repeatedPassword;
}
