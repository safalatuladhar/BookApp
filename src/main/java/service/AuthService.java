package service;

import com.google.gson.Gson;
import dto.AuthResponseDTO;
import exception.ExceptionMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import model.User;
import repository.AuthRepository;
import utils.HTTPUtils;
import utils.JWTUtils;
import utils.Validation;

import java.sql.SQLException;
import java.util.List;

public class AuthService {
    private final Gson gson;
    private final AuthRepository authRepository;
    private final UserService userService;
    private final JWTUtils jwtUtils;


    public AuthService() {
        this.gson = new Gson();
        this.authRepository = new AuthRepository();
        this.userService = new UserService();
        this.jwtUtils = new JWTUtils();
    }

    public void register(HttpServletRequest request) throws ServletException, SQLException, ClassNotFoundException {
        String requestBody = HTTPUtils.jsonParser(request);
        User user = gson.fromJson(requestBody,User.class);
        if (!Validation.isEmail(user.getEmail()) || Validation.isEmpty(user.getPassword())){
            throw new ServletException("Email or password cannot be empty");
        }
        authRepository.register(user);
    }

    public AuthResponseDTO login(HttpServletRequest request) throws ServletException, SQLException, ClassNotFoundException {
        String requestBody = HTTPUtils.jsonParser(request);
        User userDto = gson.fromJson(requestBody,User.class);
        String password = userDto.getPassword().trim();
        if (!Validation.isEmail(userDto.getEmail()) || Validation.isEmpty(password)){
            throw new ServletException(("Email or password is invalid"));
        }
        User user = authRepository.login(userDto);
        if (user == null){
            throw new ExceptionMessage(404, "User not found.");
        }
        if (!HTTPUtils.checkPasswordMatch(password, user.getPassword())){
            throw new ExceptionMessage((401), "Invalid credentials");
        }

        List<String> roles = userService.getRolesByEmail(userDto.getEmail());
        String token = jwtUtils.generateToken(user.getEmail());

        return new AuthResponseDTO(user,token,roles);
    }
}
