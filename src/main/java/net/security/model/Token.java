package net.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    private String token;
    private String type = "Bearer"; // Bearer
    private Long expiresIn;

    public Token(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
