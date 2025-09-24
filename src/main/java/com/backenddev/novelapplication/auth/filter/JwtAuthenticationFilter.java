package com.backenddev.novelapplication.auth.filter;

import com.backenddev.novelapplication.auth.service.JWTService;
import com.backenddev.novelapplication.auth.service.UserDetailsServiceImpl;
import com.backenddev.novelapplication.execption.TokenInvalidException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * validate tokens in incoming requests
 * **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    /**
     * processes each request, extracts and validates JWT, sets authentication if valid **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);

            try {
                String userName = jwtService.extractUserName(jwtToken);

                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
                    if (jwtService.isTheTokenValid(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }catch (JwtException e) {
                throw new TokenInvalidException("Invalid token or token could be expired", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
