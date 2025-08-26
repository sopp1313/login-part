package Repository;

import Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface loginRepository extends JpaRepository<Member, String> {
    Optional<Member> findbyloginId(String loginId);
    Boolean existsByid(String id);

}
