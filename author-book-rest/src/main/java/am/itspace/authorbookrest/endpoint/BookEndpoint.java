package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookcommon.dto.BookDto;
import am.itspace.authorbookcommon.dto.CreateBookRequestDto;
import am.itspace.authorbookcommon.entity.Author;
import am.itspace.authorbookcommon.entity.Book;
import am.itspace.authorbookcommon.mapper.BookMapper;
import am.itspace.authorbookcommon.repository.AuthorRepository;
import am.itspace.authorbookcommon.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Profile("dev")
public class BookEndpoint {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody CreateBookRequestDto createBookRequestDto) {
        Optional<Author> byId = authorRepository.findById(createBookRequestDto.getAuthorId());
        if(byId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Book saved = bookRepository.save(bookMapper.map(createBookRequestDto));
        saved.setAuthor(byId.get());
        return ResponseEntity.ok(bookMapper.mapToDto(saved));
    }


}