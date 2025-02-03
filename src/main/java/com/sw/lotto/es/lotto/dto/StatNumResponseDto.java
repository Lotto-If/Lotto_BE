package com.sw.lotto.es.lotto.dto;
import com.sw.lotto.es.lotto.model.StatNumDocument;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class StatNumResponseDto {
    private String id;
    private Integer cnt;
    private Integer number;
    private Integer year;

    public static StatNumResponseDto fromStatNumDoc(StatNumDocument doc) {
        return new StatNumResponseDto(
                doc.getId(),
                doc.getCnt(),
                doc.getNumber(),
                doc.getYear()
        );
    }
}
