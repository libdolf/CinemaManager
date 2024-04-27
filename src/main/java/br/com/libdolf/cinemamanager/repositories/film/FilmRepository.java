package br.com.libdolf.cinemamanager.repositories.film;

import br.com.libdolf.cinemamanager.entities.film.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
