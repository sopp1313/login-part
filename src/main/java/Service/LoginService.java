package Service;

import Repository.LoginRepository;
import Entity.Member;
import auth.JwtTokenProvider;
import auth.dto.AuthResponse;
import auth.dto.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // Lombok 대신 직접 생성자
    public LoginService(LoginRepository loginRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider jwtTokenProvider) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponse register(RegisterRequest req) {
        if (loginRepository.existsByLogInId(req.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        Member m = new Member(
                req.getLoginId(),
                passwordEncoder.encode(req.getPassword()),
                req.getName()
        );
        m = loginRepository.save(m);
        return issueTokens(m);
    }

    public AuthResponse login(String logInId, String rawPassword) {
        Member m = loginRepository.findByLogInId(logInId)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(rawPassword, m.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return issueTokens(m);
    }

    public AuthResponse refresh(String refreshToken) {
        var claims = jwtTokenProvider.parseRefresh(refreshToken).getBody();
        String userKey = claims.getSubject(); // subject = logInId
        Member m = loginRepository.findById(userKey)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return issueTokens(m);
    }

    private AuthResponse issueTokens(Member m) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", m.getName());
        claims.put("loginId", m.getLogInId());
        String access = jwtTokenProvider.createAccessToken(m.getLogInId(), claims);
        String refresh = jwtTokenProvider.createRefreshToken(m.getLogInId());
        long expiresIn = jwtTokenProvider.getAccessTtlMillis() / 1000;
        return new AuthResponse(m.getLogInId(), m.getName(), access, refresh, expiresIn);
    }
}
