package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookcommon.dto.BookDto;
import am.itspace.authorbookcommon.dto.CreateBookRequestDto;
import am.itspace.authorbookcommon.entity.Author;
import am.itspace.authorbookcommon.entity.Book;
import am.itspace.authorbookcommon.entity.Currency;
import am.itspace.authorbookcommon.mapper.BookMapper;
import am.itspace.authorbookcommon.repository.AuthorRepository;
import am.itspace.authorbookcommon.repository.BookRepository;
import am.itspace.authorbookcommon.repository.CurrencyRepository;
import am.itspace.authorbookrest.util.RoundUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
//@Profile("dev")
public class BookEndpoint {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CurrencyRepository currencyRepository;

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

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        List<Book> all = bookRepository.findAll();
        List<BookDto> bookDtos = bookMapper.mapListToDtos(all);
        List<Currency> currencies = currencyRepository.findAll();
        if (!currencies.isEmpty()) {
            Currency currency = currencies.get(0);
            for (BookDto bookDto : bookDtos) {
                double priceAmd = bookDto.getPriceAmd();
                bookDto.setPriceRub(RoundUtil.round(priceAmd/currency.getRub(), 2));
                bookDto.setPriceUsd(RoundUtil.round(priceAmd/currency.getUsd(), 1));
            }
            }
        return ResponseEntity.ok(bookDtos);
    }


}