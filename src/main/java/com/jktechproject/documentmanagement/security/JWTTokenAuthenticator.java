package com.jktechproject.documentmanagement.security;

import com.jktechproject.documentmanagement.service.RedisTokenSErvice;
import com.jktechproject.documentmanagement.service.UserDetailSErviceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTTokenAuthenticator extends OncePerRequestFilter {
    @Autowired
    private final RedisTokenSErvice redisTokenSErvice;

    private final JWTUtility jwtUtility;
    private  final UserDetailSErviceImplementation userDetailSErviceImplementation;

    public JWTTokenAuthenticator(RedisTokenSErvice redisTokenSErvice, JWTUtility jwtUtility, UserDetailSErviceImplementation userDetailSErviceImplementation) {
        this.redisTokenSErvice = redisTokenSErvice;
        this.jwtUtility = jwtUtility;
        this.userDetailSErviceImplementation = userDetailSErviceImplementation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if(header!=null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            if(redisTokenSErvice.isTokenValid(token)) {
                String userName = jwtUtility.getUserNameFromJWTToken(token);
                UserDetails userDetails = userDetailSErviceImplementation.loadUserByUsername(userName);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtility.ValidateJWTToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
                filterChain.doFilter(request, response);
            }else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not found in Redis or expired");

                return;
            }
        }else{
            filterChain.doFilter(request, response);
            return;
        }
    }
}
