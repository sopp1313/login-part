package config;

import auth.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeans {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        String accessSecret  = System.getenv().getOrDefault("ACCESS_SECRET",  "CHANGE_ME_ACCESS_SECRET_32B_MIN_LENGTH_____");
        String refreshSecret = System.getenv().getOrDefault("REFRESH_SECRET", "CHANGE_ME_REFRESH_SECRET_32B_MIN_LENGTH____");
        long accessTtlMs  = Long.parseLong(System.getenv().getOrDefault("ACCESS_TTL_MS",  String.valueOf(30*60*1000)));
        long refreshTtlMs = Long.parseLong(System.getenv().getOrDefault("REFRESH_TTL_MS", String.valueOf(7L*24*60*60*1000)));
        return new JwtTokenProvider(accessSecret, refreshSecret, accessTtlMs, refreshTtlMs);
    }
}
