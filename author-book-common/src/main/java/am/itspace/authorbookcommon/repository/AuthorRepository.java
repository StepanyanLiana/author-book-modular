package am.itspace.authorbookcommon.repository;

import am.itspace.authorbookcommon.entity.Author;
import am.itspace.authorbookcommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByEmail(String email);
    List<Author> findAllByUser(User user);
}
