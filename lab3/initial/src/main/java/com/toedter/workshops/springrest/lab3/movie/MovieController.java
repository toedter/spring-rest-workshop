package com.toedter.workshops.springrest.lab3.movie;

import com.toedter.workshops.springrest.lab3.director.Director;
import com.toedter.workshops.springrest.lab3.director.DirectorRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = HAL_FORMS_JSON_VALUE)
public class MovieController {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final MovieModelAssembler movieModelAssembler;

    MovieController(MovieRepository movieRepository, DirectorRepository directorRepository,
                    MovieModelAssembler movieModelAssembler) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.movieModelAssembler = movieModelAssembler;
    }

    @GetMapping("/movies")
    public ResponseEntity<PagedModel<EntityModel<Movie>>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {

        // prepare a paged model with movies
        final PagedModel<EntityModel<Movie>> pagedModel = null;

        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/movies")
    public ResponseEntity<EntityModel<Movie>> newMovie(@RequestBody EntityModel<Movie> movieModel) {
        // save the movie in the movie repository
        return null;
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<EntityModel<Movie>> findOne(
            @PathVariable Long id) {

        // return the movie
        return null;
    }

    @GetMapping("/movies/{id}/directors")
    public ResponseEntity<CollectionModel<Director>> findDirectors(@PathVariable Long id) {
        // return all directors of a movie
        return null;
    }

    @PatchMapping("/movies/{id}")
    public ResponseEntity<?> updateMoviePartially(
            @RequestBody EntityModel<Movie> movieModel, @PathVariable Long id) {
        // change a movie partially
        return null;
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        // delete a movie
        return ResponseEntity.noContent().build();
    }
}
