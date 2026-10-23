package com.melo.api_filmes.handler;

import com.melo.api_filmes.exception.FilmeNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FilmeNaoEncontradoException.class)
    public ProblemDetail handleFilmeNaoEncontrado(FilmeNaoEncontradoException exception) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage());
        problema.setTitle("Filme não encontrado");
        return problema;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidacao(MethodArgumentNotValidException exception) {
        Map<String, String> erros = new LinkedHashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Um ou mais campos são inválidos");
        problema.setTitle("Erro de validação");
        problema.setProperty("erros", erros);
        return problema;
    }
}