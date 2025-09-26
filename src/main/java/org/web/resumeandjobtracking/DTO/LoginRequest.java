package org.web.resumeandjobtracking.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginRequest {
    private String email;
    private String password;
}
