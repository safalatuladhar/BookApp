package dto;

import model.User;

import java.util.List;

public class AuthResponseDTO {
    private int id;
    private String email;
    private String token;
    private List<String> role;

    public AuthResponseDTO(int id, String email, String token, List<String> role) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public AuthResponseDTO(User user, String token, List<String> roles) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
