package am.itspace.authorbookcommon.dto;

import am.itspace.authorbookcommon.entity.Language;
import lombok.Data;

@Data
public class BookSearchDto {

    private String title;
    private String description;
    private String serialNumber;
    private String authorName;

    private Language language;
    private double minPrice;
    private double maxPrice;

    private String sortBy;
    private String sortDirection;
}
