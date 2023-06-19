package am.itspace.authorbookcommon.mapper;
import am.itspace.authorbookcommon.dto.CreateUserRequestDto;
import am.itspace.authorbookcommon.dto.UserDto;
import am.itspace.authorbookcommon.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(CreateUserRequestDto dto);

    UserDto mapToDto(User entity);

}