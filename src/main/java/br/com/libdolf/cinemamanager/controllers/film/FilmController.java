package br.com.libdolf.cinemamanager.controllers.film;

import br.com.libdolf.cinemamanager.controllers.film.dtos.FilmRequest;
import br.com.libdolf.cinemamanager.controllers.film.dtos.FilmResponse;
import br.com.libdolf.cinemamanager.services.film.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/films")
public class FilmController {
    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    //Public
    @GetMapping("/on-showing")
    @Operation(summary = "List all films currently showing",
            description = "The default size is 20, use the parameter size to change the default value",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<Page<FilmResponse>> findFilmsOnShowing(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(service.findFilmsOnShowing(pageable));
    }

    //Authenticated
    @GetMapping
    @Operation(summary = "Get a movie by id, title, category or list all movies if no field are passed",
            description = "The default size is 20, use the parameter size to change the default value",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<?> findFilmsByRequestParam(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @ParameterObject Pageable pageable) {

        if (id != null) {
            return ResponseEntity.ok(service.findFilmById(id));
        }
        if (title != null && category != null) {
            return ResponseEntity.ok(service.findFilmByTitleAndCategory(title, category, pageable));
        }
        if (title != null) {
            return ResponseEntity.ok(service.findFilmsByTitle(title));
        }
        if (category != null) {
            return ResponseEntity.ok(service.findFilmsByCategory(category, pageable));
        }
        return ResponseEntity.ok(service.findAllFilms(pageable));
    }

    @PostMapping
    @Operation(summary = "Send a film to be approved",
            description = "The moderator needs approve the entry",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error in request")
    })
    public ResponseEntity<FilmResponse> sendFilmToApprove(@RequestBody @Valid FilmRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.sendFilmToApprove(request));
    }

    //Moderator
    @GetMapping("/approve")
    @PreAuthorize("hasAuthority('SCOPE_MODERATOR')")
    @Operation(summary = "List all films to be approved",
            description = "The default size is 20, use the parameter size to change the default value",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<Page<FilmResponse>> findFilmsToApprove(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(service.findFilmsToApprove(pageable));
    }

    @PostMapping("/approve")
    @PreAuthorize("hasAuthority('SCOPE_MODERATOR')")
    @Operation(summary = "Approve a film by id",
            description = "Send the id as request param",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<Void> approveFilmById(@RequestParam Long id){
        service.approveFilmById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approve/all")
    @PreAuthorize("hasAuthority('SCOPE_MODERATOR')")
    @Operation(summary = "Approve all films",
            description = "Will be approve all films",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<Void> approveAllFilms(){
        service.approveAllFilms();
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_MODERATOR')")
    @Operation(summary = "Update a film",
            description = "Send the id as request param",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error in request")
    })
    public ResponseEntity<FilmResponse> updateMovie(@RequestParam Long id, @RequestBody @Valid FilmRequest request){
        return ResponseEntity.ok(service.updateFilm(id, request));
    }

    //Admin
    @DeleteMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Delete a film",
            description = "Send the id as request param",
            tags = {"Film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error in request")
    })
    public ResponseEntity<Void> deleteMovie(@RequestParam Long id){
        service.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}
