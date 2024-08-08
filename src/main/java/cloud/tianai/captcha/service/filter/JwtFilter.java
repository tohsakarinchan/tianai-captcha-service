package cloud.tianai.captcha.service.filter;

import cloud.tianai.captcha.service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = JwtUtil.getUsernameFromToken(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims = JwtUtil.getClaimsFromToken(jwt);
            if (JwtUtil.validateToken(jwt)) {
                // 这里你可以设置用户信息到SecurityContext
            }
        }

        chain.doFilter(request, response);
    }
}
