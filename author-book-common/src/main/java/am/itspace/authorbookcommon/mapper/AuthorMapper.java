package am.itspace.authorbookcommon.mapper;

import am.itspace.authorbookcommon.dto.AuthorDto;
import am.itspace.authorbookcommon.dto.CreateAuthorRequestDto;
import am.itspace.authorbookcommon.dto.CreateAuthorResponseDto;
import am.itspace.authorbookcommon.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
   // @Mapping(source = "name", target = "fullName")
    Author map(CreateAuthorRequestDto dto);
    CreateAuthorResponseDto map(Author entity);
   // @Mapping(target = "fullName", expression = "java(entity.getName() + ' ' + entity.getSurname())")
    @Mapping(target = "fullName", source = "entity", qualifiedByName = "fullNameMapping")
    AuthorDto mapToDto(Author entity);

    @Named("fullNameMapping")
    default String getFullName(Author entity) {
        return entity.getName() + " " + entity.getSurname();
    }


}

















