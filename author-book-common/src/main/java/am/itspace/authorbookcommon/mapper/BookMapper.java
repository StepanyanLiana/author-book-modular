package am.itspace.authorbookcommon.mapper;
import am.itspace.authorbookcommon.dto.BookDto;
import am.itspace.authorbookcommon.dto.CreateBookRequestDto;
import am.itspace.authorbookcommon.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Value("${site.url}")
    String siteUrl;

    @Mapping(target = "author.id", source = "authorId")
    public abstract Book map(CreateBookRequestDto dto);

    @Mapping(target = "authorDto", source = "author")
    @Mapping(target = "picUrl", expression = "java(entity.getPicName() != null ? siteUrl + \"/books/getImage?picName=\" + entity.getPicName() : null)")
    public abstract BookDto mapToDto(Book entity);

    public abstract List<BookDto> mapListToDtos(List<Book> books);

}