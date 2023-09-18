package filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.HTTPUtils;
import utils.JWTUtils;

import java.io.IOException;

@WebFilter(filterName = "JWTFilter")
public class JWTFilter implements Filter {

    private final JWTUtils jwtUtils;

    public JWTFilter() {
        this.jwtUtils = new JWTUtils();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");

        String path = request.getRequestURI();
        boolean allowed = HTTPUtils.isUrlAllowed(path);

        if (allowed) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            HTTPUtils.sendErrorResponse(response, 403, "Resources access denied!");
        }

        final String jwtToken = authHeader.substring(7);

        try {
            if (!jwtUtils.isTokenValid(jwtToken)) {
                HTTPUtils.sendErrorResponse(response, 400, "Resources access denied!");
                return;
            }
        } catch (ExpiredJwtException e) {
            HTTPUtils.sendErrorResponse(response, 403, "Token expired. Login again!");
            return;
        }
        servletRequest.setAttribute("token", jwtToken);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
