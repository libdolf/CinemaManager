package br.com.libdolf.cinemamanager.services.film;

import br.com.libdolf.cinemamanager.controllers.film.dtos.FilmRequest;
import br.com.libdolf.cinemamanager.controllers.film.dtos.FilmResponse;
import br.com.libdolf.cinemamanager.exceptions.FilmNotFoundException;
import br.com.libdolf.cinemamanager.mapper.FilmMapper;
import br.com.libdolf.cinemamanager.repositories.film.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmService {
    private final FilmRepository repository;
    private final FilmMapper mapper;

    public FilmService(FilmRepository repository, FilmMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<FilmResponse> findFilmsOnShowing(Pageable pageable) {
       var films = repository.findFilmsByIsOnShowingTrue(pageable);
       return films.map(mapper::toResponse);
    }

    public FilmResponse findFilmById(Long id) {
        var film = repository.findById(id).orElseThrow(FilmNotFoundException::new);
        return mapper.toResponse(film);
    }

    public FilmResponse findFilmsByTitle(String title) {
        var film = repository.findFilmByTitle(title).orElseThrow(FilmNotFoundException::new);
        return mapper.toResponse(film);
    }

    public Page<FilmResponse> findFilmsByCategory(String category, Pageable pageable) {
        var film = repository.findFilmsByCategoriesIsContainingIgnoreCase(category, pageable);
        return film.map(mapper::toResponse);
    }

    public Page<FilmResponse> findFilmByTitleAndCategory(String title, String category, Pageable pageable) {
        var film = repository.findFilmsByTitleContainingIgnoreCaseAndCategoriesContainingIgnoreCase(title, category, pageable);
        return film.map(mapper::toResponse);
    }

    public Page<FilmResponse> findAllFilms(Pageable pageable) {
        var films = repository.findAll(pageable);
        return films.map(mapper::toResponse);
    }

    public FilmResponse sendFilmToApprove(FilmRequest request) {
        var film = mapper.toEntity(request);
        return mapper.toResponse(repository.save(film));
    }

    public Page<FilmResponse> findFilmsToApprove(Pageable pageable) {
        var films = repository.findFilmsByIsApprovedFalse(pageable);
        return films.map(mapper::toResponse);
    }

    public void approveFilmById(Long id) {
        var film = repository.findById(id).orElseThrow(FilmNotFoundException::new);
        film.setIsApproved(true);
        repository.save(film);
    }

    public void approveAllFilms() {
        var films = repository.findFilmsByIsApprovedFalse();
        films.forEach(f -> f.setIsApproved(true));
        repository.saveAll(films);
    }

    public FilmResponse updateFilm(Long id, FilmRequest request) {
        var film = repository.findById(id).orElseThrow(FilmNotFoundException::new);
        film.update(request);
        return mapper.toResponse(repository.save(film));
    }

    public void deleteMovie(Long id) {
        repository.findById(id).orElseThrow(FilmNotFoundException::new);
        repository.deleteById(id);
    }
}
