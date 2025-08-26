package Service;

import Entity.Member;
import Repository.loginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final loginRepository repository;

    public Optional<Member> login(String loginId, String password) {
        return repository.findbyloginId(loginId).filter(m->m.getPassword().equals(password));
    }
}
