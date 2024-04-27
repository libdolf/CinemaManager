package br.com.libdolf.cinemamanager.repositories.film;

import br.com.libdolf.cinemamanager.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Page<Film> findFilmsByIsOnShowingTrue(Pageable pageable);
    Optional<Film> findFilmByTitle(String title);
    Page<Film> findFilmsByCategoriesIsContainingIgnoreCase(String categories, Pageable pageable);
    Page<Film> findFilmsByTitleContainingIgnoreCaseAndCategoriesContainingIgnoreCase(String title, String category, Pageable pageable);
    Page<Film> findFilmsByIsApprovedFalse(Pageable pageable);
    List<Film> findFilmsByIsApprovedFalse();



}
