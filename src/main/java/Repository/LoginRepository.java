package Repository;

import Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Member, String> {
    Optional<Member> findByLogInId(String logInId);
    boolean existsByLogInId(String logInId);
}
