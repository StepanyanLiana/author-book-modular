package am.itspace.authorbookcommon.mapper;
import am.itspace.authorbookcommon.dto.BookDto;
import am.itspace.authorbookcommon.dto.CreateBookRequestDto;
import am.itspace.authorbookcommon.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    @Mapping(target = "author.id", source = "authorId")
    Book map(CreateBookRequestDto dto);

    @Mapping(target = "authorDto", source = "author")
    BookDto mapToDto(Book entity);


}