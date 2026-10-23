package com.melo.api_filmes.controller;

import com.melo.api_filmes.dto.FilmeDto;
import com.melo.api_filmes.dto.FilmePatchDto;
import com.melo.api_filmes.dto.FilmeResponseDto;
import com.melo.api_filmes.service.FilmeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/filmes")
@RequiredArgsConstructor
public class FilmeController {
    private final FilmeService filmeService;

    @GetMapping
    public List<FilmeResponseDto> listarTodos() {
        return filmeService.listarTodos();
    }

    @GetMapping("/{id}")
    public FilmeResponseDto buscarPorId(@PathVariable Integer id) {
        return filmeService.buscarFilmePorId(id);
    }

    @GetMapping("/melhores-avaliados")
    public List<FilmeResponseDto> listarFilmesMaisBemAvaliados() {
        return filmeService.listarFilmesMaisBemAvaliados();
    }

    @GetMapping("/melhor-avaliado")
    public FilmeResponseDto listarFilmeMelhorAvaliado() {
        return filmeService.listarFilmeMelhorAvaliado();
    }

    @GetMapping("/pior-avaliado")
    public FilmeResponseDto listarFilmePiorAvaliado() {
        return filmeService.listarFilmePiorAvaliado();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmeResponseDto criarFilme(@Valid @RequestBody FilmeDto filmeDto) {
        return filmeService.criarFilme(filmeDto);
    }

    @PutMapping("/{id}")
    public FilmeResponseDto atualizarFilme(@PathVariable Integer id, @Valid @RequestBody FilmeDto filmeDto) {
        return filmeService.atualizarFilme(filmeDto, id);
    }

    @PatchMapping("/{id}")
    public FilmeResponseDto atualizarParcialmente(@PathVariable Integer id, @Valid @RequestBody FilmePatchDto filmeDto) {
        return filmeService.atualizarParcialmente(filmeDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarFilme(@PathVariable Integer id) {
        filmeService.deletarFilme(id);
    }
}