package com.backenddev.novelapplication.filters;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        System.out.println("Request URL: " + httpRequest.getRequestURL() + " "
                + httpRequest.getMethod());

        System.out.println("Response URL: " + httpResponse.getStatus());

        chain.doFilter(request, response);
    }
}
