package spring.boot.rsa512.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
