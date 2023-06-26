package am.itspace.authorbookcommon.dto;


import am.itspace.authorbookcommon.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private int id;
    private String description;
    private Language language;
    private AuthorDto authorDto;
    private double priceAmd;
    private double priceUsd;
    private double priceRub;
}
