package br.com.libdolf.cinemamanager.mapper;

import br.com.libdolf.cinemamanager.controllers.film.dtos.FilmRequest;
import br.com.libdolf.cinemamanager.controllers.film.dtos.FilmResponse;
import br.com.libdolf.cinemamanager.entities.film.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FilmMapper {
    public abstract FilmResponse toResponse(Film film);
    public abstract Film toEntity(FilmRequest request);

}
