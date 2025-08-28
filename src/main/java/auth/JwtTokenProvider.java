package auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {
    private final SecretKey accessKey;
    private final SecretKey refreshKey;
    private final long accessTtlMillis;
    private final long refreshTtlMillis;
    public JwtTokenProvider(String accessSecret, String refreshSecret, long accessTtlMillis, long refreshTtlMillis) {
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        this.accessTtlMillis = accessTtlMillis;
        this.refreshTtlMillis = refreshTtlMillis;
    }
    public String createAccessToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder().setSubject(subject).addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(accessTtlMillis)))
                .signWith(accessKey, SignatureAlgorithm.HS256).compact();
    }
    public String createRefreshToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder().setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(refreshTtlMillis)))
                .signWith(refreshKey, SignatureAlgorithm.HS256).compact();
    }
    public Jws<Claims> parseAccess(String token) {
        return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token);
    }
    public Jws<Claims> parseRefresh(String token) {
        return Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token);
    }
    public long getAccessTtlMillis() { return accessTtlMillis; }
}
