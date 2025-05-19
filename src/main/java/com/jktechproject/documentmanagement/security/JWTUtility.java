package com.jktechproject.documentmanagement.security;

import com.jktechproject.documentmanagement.repository.UserRepoRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTUtility {

    @Autowired
    private UserRepoRole userRepoRole;
    private final SecretKey secretKey;

   // private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long tokenTimeOut = 3600000;

    public JWTUtility() {
        String secret = "bXktMjU2LWJpdC1zZWNyZXQtYXV0hdjghdgdgXktMTIzNA==";
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJWTToken(String userName){
        return Jwts.builder().setSubject(userName).
                claim("roles", getUserRoles(userName)).
                setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis() + tokenTimeOut)).
                signWith(secretKey,SignatureAlgorithm.HS256).compact();
    }

    private Claims getParseValue(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public Boolean ValidateJWTToken(String token, UserDetails userDetails){
        try
        {
            getParseValue(token);
            System.out.println("Token Validated successfully "+token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e)
        {
            System.out.println("Invalid JWT Token: " + e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJWTToken(String token){
        return getParseValue(token).getSubject();
    }

    public Set<String> getUserRoleFromJWTToken(String token){
        Claims claim = getParseValue(token);
        Object roleSet = claim.get("roles");
        if (roleSet instanceof List<?> rolesList) {
            return rolesList.stream().map(Object::toString).collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    public Set<String> getUserRoles(String userName) {
        return userRepoRole.findByUsername(userName)
                .map(user -> user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }
}
