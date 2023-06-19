package am.itspace.authorbookcommon.dto;

import am.itspace.authorbookcommon.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequestDto {
    private String title;
    private String description;
    private Language language;
    private String serialNumber;
    private int authorId;
}
