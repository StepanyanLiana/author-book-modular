package am.itspace.authorbookcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

}