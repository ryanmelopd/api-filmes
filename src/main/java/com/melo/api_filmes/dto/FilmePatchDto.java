package com.melo.api_filmes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmePatchDto {

    @Size(max = 150)
    private String titulo;

    @Size(max = 100)
    private String genero;

    @Min(value = 1888)
    @Max(value = 2100)
    private Integer anoDeLancamento;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double notaIMDB;
}