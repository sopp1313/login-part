package auth.dto;

public class AuthResponse {
    private String userId;
    private String name;
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    public AuthResponse() {}
    public AuthResponse(String userId, String name, String accessToken, String refreshToken, long expiresIn) {
        this.userId = userId;
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
}
