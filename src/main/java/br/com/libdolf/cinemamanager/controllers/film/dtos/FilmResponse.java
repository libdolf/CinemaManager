package br.com.libdolf.cinemamanager.controllers.film.dtos;



import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record FilmResponse(
         Long id,
         String title,
         String description,
         String director,
         LocalDate releaseDate,
         LocalDate endDate,
         Boolean isOnShowing,
         List<String> categories,
         Instant creationTimestamp,
         Instant updateTimestamp
) {
}
