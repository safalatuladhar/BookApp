package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import utils.HTTPUtils;
import utils.JWTUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "RoleFilter")
public class RoleFilter implements Filter {

    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final Map<String,String[]>roleMap;

    public RoleFilter() {
        this.userService = new UserService();
        this.jwtUtils = new JWTUtils();
        roleMap = new HashMap<>();
        roleMap.put("^/admin/.*$", new String[]{"ADMIN"});
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();
        boolean allowed = HTTPUtils.isUrlAllowed(path);
        if (allowed) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final String jwtToken = (String) servletRequest.getAttribute("token");
        String email = jwtUtils.extractEmail(jwtToken);
        List<String>roles = null;
        if (roles.size() == 0){
            HTTPUtils.sendErrorResponse(response, 418, "No privileges assigned");
            return;
        }

        for (Map.Entry<String,String[]> entry : roleMap.entrySet()){
            String pattern = entry.getKey();
            if (path.matches(pattern)){
                String[] urlRoles = entry.getValue();
                for (String urlRole: urlRoles){
                    if (roles.contains(urlRole)){
                        filterChain.doFilter(servletRequest,servletResponse);
                        return;
                    }
                }
            }else {
                HTTPUtils.sendErrorResponse(response, 403, "Full authentication is needed");
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
