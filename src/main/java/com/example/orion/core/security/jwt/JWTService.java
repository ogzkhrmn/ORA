package com.example.orion.core.security.jwt;

import com.example.orion.constants.GeneralMessageConstants;
import com.example.orion.core.exception.ConfigException;
import com.example.orion.core.i18n.Translator;
import com.example.orion.entities.User;
import com.example.orion.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

@Service
public class JWTService {

    @Autowired
    private JWTUtil util;

    @Autowired
    private UserRepository userRepository;

    private Key signingKey;

    private JwtParser parser;

    /*
     * Initialize jwt service
     */
    @PostConstruct
    private void initialize() {
        signingKey = new SecretKeySpec(util.getSecret().getBytes(), SignatureAlgorithm.HS512.getJcaName());
        parser = Jwts.parserBuilder().setSigningKey(signingKey).build();
    }

    /*
     * Creates token for logged in user
     *
     * @param user user details
     * @return user token
     */
    public String createToken(User user) {
        return util.getTokenPrefix() + Jwts.builder()
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusDays(1)))
                .claim("username", user.getUsername().toLowerCase())
                .claim("roles", user.getRole().getRole())
                .claim("id", user.getId())
                .signWith(signingKey)
                .compact();
    }

    /*
     * Parses token and returns user id
     *
     * @param token user token
     * @return user id
     */
    public Long getIdFromToken(String token) {
        Claims claims = getJwtToken(token);
        return claims.get("id", Double.class).longValue();
    }

    /*
     * Parses token and returns user data
     *
     * @return user data
     */
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    /*
     * Parses token and returns user data
     *
     * @return user data
     */
    public List<SimpleGrantedAuthority> getUserRoles(User user) {
        Set<String> roles = Set.of(user.getRole().getRole());

        return Arrays.stream(roles.toArray(new String[0]))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /*
     * Parses token and returns user data
     *
     * @param token user token
     * @return user data
     */
    public User getUserFromToken(String token) {
        return findById(getIdFromToken(token));
    }

    /*
     * Generates user authorities
     *
     * @param userDetails details
     * @return spring object
     */
    public UsernamePasswordAuthenticationToken getAuthenticationToken(final User userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", getUserRoles(userDetails));
    }

    /*
     * Parses token and returns token data
     *
     * @param token user token
     * @return like user data, id, expire data etc.
     */
    private Claims getJwtToken(String token) {
        try {
            String reToken = token.replace(util.getTokenPrefix(), "");
            return parser.parseClaimsJws(reToken).getBody();
        } catch (MalformedJwtException | ExpiredJwtException e) {
            throw new ConfigException(HttpStatus.UNAUTHORIZED, "JWT token is invalid", "JWT_TOKEN_INVALID");
        }

    }

    /*
     * Chceks if token expired
     *
     * @param token user token
     * @return expired true, not expired false
     */
    public boolean isTokenExpired(String token) {
        Claims claims = getJwtToken(token);
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(claims.getExpiration().getTime()), TimeZone.getDefault().toZoneId());
        return time.isBefore(LocalDateTime.now());
    }

    /*
     * This method creates new individual user
     *
     * @param id of user
     */
    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ConfigException(HttpStatus.BAD_REQUEST, Translator.getMessage(GeneralMessageConstants.USER_NOT_FOUND),
                        GeneralMessageConstants.USR_NOT_FOUND));
    }

}

