package utils;

import com.google.gson.Gson;
import dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.BCrypt;
import utils.Constants;

import java.io.IOException;

public class HTTPUtils {

    public static String jsonParser(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try {
            String json;
            while ((json = request.getReader().readLine()) != null) {
                sb.append(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPasswordMatch(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static void sendErrorResponse(HttpServletResponse response, int status, String message) {
        try {
            ErrorResponseDTO err = new ErrorResponseDTO(status, message);
            response.setStatus(status);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(new Gson().toJson(err));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void sendResponse(HttpServletResponse response, Object object) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(new Gson().toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean isUrlAllowed(String url) {
        boolean allowed = false;
        for (String pattern : Constants.ALLOWED_PATHS) {
            boolean check = url.matches(pattern);
            if (check) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }
}
