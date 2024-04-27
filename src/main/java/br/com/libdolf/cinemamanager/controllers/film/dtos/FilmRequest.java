package br.com.libdolf.cinemamanager.controllers.film.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record FilmRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String director,
        @NotNull  LocalDate releaseDate,
        @NotNull  LocalDate endDate,
        @NotNull  List<String>categories
) {
}
