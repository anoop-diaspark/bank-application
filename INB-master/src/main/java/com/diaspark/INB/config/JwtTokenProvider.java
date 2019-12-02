package com.diaspark.INB.config;

import com.diaspark.INB.entity.UserPrincipal;
import com.diaspark.INB.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    //The JWT signature algorithm we will be using to sign the token
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public String generateToken(UserPrincipal userPrincipal) throws JsonProcessingException {

        String subject = jacksonObjectMapper.writeValueAsString(userPrincipal);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(userPrincipal.getNow())
                .setExpiration(userPrincipal.getExpireAt())
                .signWith(signatureAlgorithm, getSigningKey())
                .compact();
    }

    public UserPrincipal getUserPrincipal(String token) {
        try {
            Claims body = getClaims(token);
            return jacksonObjectMapper.readValue(body.getSubject(), UserPrincipal.class);
        } catch (Exception e) {
            throw new UnauthorizedException("Failed to parse token", e);
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    //Generate signing key for jwt
    private Key getSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

}
