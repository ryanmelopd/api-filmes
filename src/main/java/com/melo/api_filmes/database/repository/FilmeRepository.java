package com.melo.api_filmes.database.repository;

import com.melo.api_filmes.database.model.FilmeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<FilmeModel, Integer> {

    List<FilmeModel> findByNotaIMDBGreaterThanEqual(Double nota);

    Optional<FilmeModel> findFirstByNotaIMDBIsNotNullOrderByNotaIMDBDesc();

    Optional<FilmeModel> findFirstByNotaIMDBIsNotNullOrderByNotaIMDBAsc();
}