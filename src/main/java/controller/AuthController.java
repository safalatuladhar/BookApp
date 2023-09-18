package controller;

import dto.AuthResponseDTO;
import exception.ExceptionMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthService;
import utils.HTTPUtils;

import java.sql.SQLException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {

    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        String requestType = request.getParameter("type");
        if (requestType.equals("login")){
            try {
                AuthResponseDTO authResponseDTO = authService.login(request);
                HTTPUtils.sendResponse(response, authResponseDTO);
            } catch (ExceptionMessage e) {
                HTTPUtils.sendErrorResponse(response,e.getStatusCode(),e.getMessage());
            } catch (ServletException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            try {
                authService.register(request);
            } catch (ExceptionMessage e) {
                HTTPUtils.sendErrorResponse(response, e.getStatusCode(), e.getMessage());
            } catch (ServletException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
