package service;

import jakarta.servlet.ServletException;
import repository.UserRepository;
import utils.Validation;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public List<String>getRolesByEmail(String email) throws ServletException {
        if (!Validation.isEmail(email)){
            throw new ServletException("Email or password is invalid!");
        }
        return userRepository.getUserRoles(email);
    }
}
