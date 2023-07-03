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
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    @Value("${upload.image.path}")
    private String uploadPath;
    @Value("${site.url}")
    private String siteUrl;

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
    @PostMapping("/{id}/image")
    public ResponseEntity<BookDto> uploadImage(@PathVariable("id") int bookId,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!multipartFile.isEmpty() && bookOptional.isPresent()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String picName = System.currentTimeMillis() + "_" + originalFilename;
            File file = new File(uploadPath + picName);
            multipartFile.transferTo(file);
            Book book = bookOptional.get();
            book.setPicName(picName);
            bookRepository.save(book);
            BookDto bookDto = bookMapper.mapToDto(book);
            //bookDto.setPicUrl(siteUrl + "/books/getImage?picName=" + picName);
            return ResponseEntity.ok(bookDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/getImage",
    produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        File file = new File(uploadPath + picName);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            return IOUtils.toByteArray(fileInputStream);
        }
        return null;
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