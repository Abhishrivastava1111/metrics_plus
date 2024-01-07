package com.taskmaster.manager.filter;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.taskmaster.manager.dto.RequestToken;
import com.taskmaster.manager.entity.User;
import com.taskmaster.manager.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Autowired
    private UserRepository userDao;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", getRolesFromAuthorities(userDetails.getAuthorities()));
        return createToken(claims, userDetails.getUsername());
    }

    public String generateRefereshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", getRolesFromAuthorities(userDetails.getAuthorities()));
        return createRefreshToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String userName) {
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))/// changed the time of the token
                /// expiry
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        return accessToken;
    }

    private String createRefreshToken(Map<String, Object> claims, String userName) {
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))/// changed the time of the token
                                                                                     /// expiry
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        return refreshToken;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Collection<String> getRolesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token, UserDetails userdetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userdetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isValidRefreshToken(RequestToken refreshToken, String name) {
        final String userName = extractUsername(refreshToken.getRefToken());
        return (userName.equals(name) && !isTokenExpired(refreshToken.getRefToken()));
    }

    public String generateTokenbyBareEmail(String userDetails) {
        Map<String, Object> claims = new HashMap<>();
        User user = userDao.findByEmail(userDetails).get(0);
        Collection<String> roles = user.getUserRoles().stream().map(m -> m.getRole().getName())
                .collect(Collectors.toList());
        claims.put("roles", roles);
        return createToken(claims, userDetails);
    }
}