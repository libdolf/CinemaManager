package br.com.libdolf.cinemamanager.entities.film;

import br.com.libdolf.cinemamanager.controllers.film.dtos.FilmRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String director;
    private LocalDate releaseDate;
    private LocalDate endDate;
    private Boolean isOnShowing;
    @ElementCollection
    private List<String> categories;
    @CreationTimestamp
    private Instant creationTimestamp;
    @UpdateTimestamp
    private Instant updateTimestamp;
    private Boolean isApproved = false;

    public Film(String title, String description, String director, LocalDate releaseDate, LocalDate endDate, List<String> categories) {
        this.title = title;
        this.description = description;
        this.director = director;
        this.releaseDate = releaseDate;
        this.endDate = endDate;
        this.isOnShowing = isOnShowing();
        this.categories = categories;
    }


    public Boolean isOnShowing(){
        var dateNow = LocalDate.now();

        return (dateNow.isEqual(releaseDate) || dateNow.isAfter(endDate)) &&
                (dateNow.isEqual(endDate) || dateNow.isBefore(endDate));
    }

    public void update(FilmRequest request) {
        this.title = request.title();
        this.description = request.description();
        this.director = request.director();
        this.releaseDate = request.releaseDate();
        this.endDate = request.endDate();
        this.isOnShowing = isOnShowing();
        this.categories = request.categories();
    }
}
