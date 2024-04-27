package br.com.libdolf.cinemamanager.entities.film;

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
    private Boolean isInTheaters;
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
        this.isInTheaters = isInTheaters();
        this.categories = categories;
    }


    public Boolean isInTheaters(){
        var dateNow = LocalDate.now();

        return (dateNow.isEqual(releaseDate) || dateNow.isAfter(endDate)) &&
                (dateNow.isEqual(endDate) || dateNow.isBefore(endDate));
    }
}
