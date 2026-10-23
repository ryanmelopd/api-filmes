package com.melo.api_filmes.dto;

import com.melo.api_filmes.database.model.FilmeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilmeResponseDto {
    private Integer id;
    private String titulo;
    private String genero;
    private Integer anoDeLancamento;
    private Double notaIMDB;

    public static FilmeResponseDto deModelo(FilmeModel filme) {
        return FilmeResponseDto.builder()
                .id(filme.getId())
                .titulo(filme.getTitulo())
                .genero(filme.getGenero())
                .anoDeLancamento(filme.getAnoDeLancamento())
                .notaIMDB(filme.getNotaIMDB())
                .build();
    }
}