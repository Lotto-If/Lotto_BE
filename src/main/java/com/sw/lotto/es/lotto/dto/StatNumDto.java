package com.sw.lotto.es.lotto.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatNumDto {
    private String id;
    private Integer cnt;
    private Integer number;
    private Integer year;
}
