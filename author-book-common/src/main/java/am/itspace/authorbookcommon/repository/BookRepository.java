package am.itspace.authorbookcommon.repository;

import am.itspace.authorbookcommon.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
