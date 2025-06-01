package ua.rdev.nure.mppzbackend.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.rdev.nure.mppzbackend.exceptions.InvalidSessionException;
import ua.rdev.nure.mppzbackend.responses.ErrorMessageResponse;

import java.io.IOException;

@Component
public class InvalidSessionHandlerFilter extends OncePerRequestFilter {
    public static final String errorResponse;

    static {
        ErrorMessageResponse resp = new ErrorMessageResponse("Invalid session");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            errorResponse = objectMapper.writeValueAsString(resp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (InvalidSessionException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(errorResponse);
        }
    }

}
