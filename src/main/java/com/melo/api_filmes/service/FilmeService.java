package com.melo.api_filmes.service;

import com.melo.api_filmes.database.model.FilmeModel;
import com.melo.api_filmes.database.repository.FilmeRepository;
import com.melo.api_filmes.dto.FilmeDto;
import com.melo.api_filmes.dto.FilmePatchDto;
import com.melo.api_filmes.dto.FilmeResponseDto;
import com.melo.api_filmes.exception.FilmeNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilmeService {

    private static final double NOTA_MINIMA_MELHORES_AVALIADOS = 8.0;

    private final FilmeRepository filmeRepository;

    public List<FilmeResponseDto> listarTodos() {
        return filmeRepository.findAll().stream()
                .map(FilmeResponseDto::deModelo)
                .toList();
    }

    public FilmeResponseDto buscarFilmePorId(Integer id) {
        return FilmeResponseDto.deModelo(buscarEntidadePorId(id));
    }

    public List<FilmeResponseDto> listarFilmesMaisBemAvaliados() {
        return filmeRepository.findByNotaIMDBGreaterThanEqual(NOTA_MINIMA_MELHORES_AVALIADOS).stream()
                .map(FilmeResponseDto::deModelo)
                .toList();
    }

    public FilmeResponseDto listarFilmeMelhorAvaliado() {
        FilmeModel filme = filmeRepository.findFirstByNotaIMDBIsNotNullOrderByNotaIMDBDesc()
                .orElseThrow(() -> new FilmeNaoEncontradoException("Nenhum filme com nota cadastrado"));
        return FilmeResponseDto.deModelo(filme);
    }

    public FilmeResponseDto listarFilmePiorAvaliado() {
        FilmeModel filme = filmeRepository.findFirstByNotaIMDBIsNotNullOrderByNotaIMDBAsc()
                .orElseThrow(() -> new FilmeNaoEncontradoException("Nenhum filme com nota cadastrado"));
        return FilmeResponseDto.deModelo(filme);
    }

    @Transactional
    public FilmeResponseDto criarFilme(FilmeDto filmeDto) {
        FilmeModel filme = FilmeModel.builder()
                .titulo(filmeDto.getTitulo())
                .genero(filmeDto.getGenero())
                .anoDeLancamento(filmeDto.getAnoDeLancamento())
                .notaIMDB(filmeDto.getNotaIMDB())
                .build();

        return FilmeResponseDto.deModelo(filmeRepository.save(filme));
    }

    @Transactional
    public FilmeResponseDto atualizarFilme(FilmeDto filmeDto, Integer id) {
        FilmeModel filme = buscarEntidadePorId(id);

        filme.setTitulo(filmeDto.getTitulo());
        filme.setGenero(filmeDto.getGenero());
        filme.setAnoDeLancamento(filmeDto.getAnoDeLancamento());
        filme.setNotaIMDB(filmeDto.getNotaIMDB());

        return FilmeResponseDto.deModelo(filmeRepository.save(filme));
    }

    @Transactional
    public FilmeResponseDto atualizarParcialmente(FilmePatchDto filmeDto, Integer id) {
        FilmeModel filme = buscarEntidadePorId(id);

        if (filmeDto.getTitulo() != null) {
            filme.setTitulo(filmeDto.getTitulo());
        }
        if (filmeDto.getGenero() != null) {
            filme.setGenero(filmeDto.getGenero());
        }
        if (filmeDto.getAnoDeLancamento() != null) {
            filme.setAnoDeLancamento(filmeDto.getAnoDeLancamento());
        }
        if (filmeDto.getNotaIMDB() != null) {
            filme.setNotaIMDB(filmeDto.getNotaIMDB());
        }

        return FilmeResponseDto.deModelo(filmeRepository.save(filme));
    }

    @Transactional
    public void deletarFilme(Integer id) {
        FilmeModel filme = buscarEntidadePorId(id);
        filmeRepository.delete(filme);
    }

    private FilmeModel buscarEntidadePorId(Integer id) {
        return filmeRepository.findById(id)
                .orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado com id: " + id));
    }
}