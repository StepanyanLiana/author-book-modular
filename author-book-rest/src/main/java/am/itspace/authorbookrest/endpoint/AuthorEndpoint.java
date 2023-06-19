package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookcommon.dto.AuthorDto;
import am.itspace.authorbookcommon.dto.CreateAuthorRequestDto;
import am.itspace.authorbookcommon.dto.CreateAuthorResponseDto;
import am.itspace.authorbookcommon.entity.Author;
import am.itspace.authorbookcommon.entity.User;
import am.itspace.authorbookcommon.mapper.AuthorMapper;
import am.itspace.authorbookcommon.repository.AuthorRepository;
import am.itspace.authorbookrest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorEndpoint {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @PostMapping()
    public ResponseEntity<CreateAuthorResponseDto> create(@RequestBody CreateAuthorRequestDto requestDto,
                                                          @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();

        Optional<Author> byEmail = authorRepository.findByEmail(requestDto.getEmail());
        if (byEmail.isEmpty()) {
            Author author = authorMapper.map(requestDto);
            author.setUser(user);
            authorRepository.save(author);

            return ResponseEntity.ok(authorMapper.map(author));
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @GetMapping()
    public ResponseEntity<List<AuthorDto>> getAll(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Author> all = authorRepository.findAllByUser(user);
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<AuthorDto> authorDtos = new ArrayList<>();
        for (Author author : all) {
            authorDtos.add(authorMapper.mapToDto(author));
        }
        return ResponseEntity.ok(authorDtos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable("id") int id, @RequestBody Author author) {
        Optional<Author> byId = authorRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Author> byEmail = authorRepository.findByEmail(author.getEmail());
        if (byEmail.isPresent() && byEmail.get().getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Author authorFromDB = byId.get();
        if (author.getName() != null && !author.getName().isEmpty()) {
            authorFromDB.setName(author.getName());
        }
        if (author.getSurname() != null && !author.getSurname().isEmpty()) {
            authorFromDB.setSurname(author.getSurname());
        }
        if (author.getEmail() != null && !author.getEmail().isEmpty()) {
            authorFromDB.setEmail(author.getEmail());
        }
        return ResponseEntity.ok(authorRepository.save(authorFromDB));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") int id) {
        Optional<Author> byId = authorRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

}