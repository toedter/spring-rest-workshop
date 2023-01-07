package com.toedter.workshops.springrest.lab4.movie;

import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import com.toedter.workshops.springrest.lab4.director.Director;
import com.toedter.workshops.springrest.lab4.director.DirectorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder.jsonApiModel;
import static com.toedter.spring.hateoas.jsonapi.MediaTypes.JSON_API_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = JSON_API_VALUE)
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
    public ResponseEntity<RepresentationModel<?>> findAll(
            @RequestParam(value = "page[number]", defaultValue = "0", required = false) int page,
            @RequestParam(value = "page[size]", defaultValue = "10", required = false) int size) {

        final PageRequest pageRequest = PageRequest.of(page, size);

        final Page<Movie> pagedResult = movieRepository.findAll(pageRequest);

        List<? extends RepresentationModel<?>> movieResources =
                StreamSupport.stream(pagedResult.spliterator(), false)
                        .map((Movie movie1) -> movieModelAssembler.toModel(movie1, false))
                        .collect(Collectors.toList());

        final Affordance newMovieAffordance =
                afford(methodOn(MovieController.class).newMovie(null));

        Link selfLink = linkTo(MovieController.class).slash("movies?"
                + "page=" + pagedResult.getNumber()
                + "&size=" + pagedResult.getSize()).withSelfRel().andAffordance(newMovieAffordance);

        PagedModel.PageMetadata pageMetadata =
                new PagedModel.PageMetadata(
                        pagedResult.getSize(),
                        pagedResult.getNumber(),
                        pagedResult.getTotalElements(),
                        pagedResult.getTotalPages());

        final PagedModel<? extends RepresentationModel<?>> pagedModel =
                PagedModel.of(movieResources, pageMetadata);

        String pageLinksBase =
                linkTo(MovieController.class).slash("movies").withSelfRel().getHref();

        final JsonApiModelBuilder jsonApiModelBuilder =
                jsonApiModel().model(pagedModel).link(selfLink).pageLinks(pageLinksBase);

        for (Movie movie : pagedResult.getContent()) {
            jsonApiModelBuilder.included(movie.getDirectors());
        }

        return ResponseEntity.ok(jsonApiModelBuilder.build());
    }

    @PostMapping("/movies")
    public ResponseEntity<?> newMovie(@RequestBody EntityModel<Movie> movieModel) {
        Movie movie = movieModel.getContent();
        assert movie != null;
        movieRepository.save(movie);

        List<Director> directorsWithIds = movie.getDirectors();
        List<Director> directors = new ArrayList<>();
        movie.setDirectors(directors);

        for (Director directorWithId : directorsWithIds) {
            directorRepository.findById(directorWithId.getId()).map(director -> {
                director.addMovie(movie);
                directorRepository.save(director);
                movie.addDirector(director);
                return director;
            });
        }
        movieRepository.save(movie);

        final RepresentationModel<?> movieRepresentationModel = movieModelAssembler.toModel(movie,false);

        return movieRepresentationModel
                .getLink(IanaLinkRelations.SELF)
                .map(Link::getHref)
                .map(href -> {
                    try {
                        return new URI(href);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(uri -> ResponseEntity.created(uri).body(movieRepresentationModel))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create " + movie));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<? extends RepresentationModel<?>> findOne(
            @PathVariable Long id) {

        return movieRepository.findById(id)
                .map((Movie movie) -> movieModelAssembler.toModel(movie, true))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/movies/{id}/directors")
    public ResponseEntity<? extends RepresentationModel<?>> findDirectors(@PathVariable Long id) {

        return movieRepository.findById(id)
                .map(movieModelAssembler::directorsToModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/movies/{id}")
    public ResponseEntity<?> updateMoviePartially(@RequestBody EntityModel<Movie> movieModel, @PathVariable Long id) {

        Movie existingMovie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        Movie movie = movieModel.getContent();
        existingMovie.update(movie);

        movieRepository.save(existingMovie);
        final RepresentationModel<?> movieRepresentationModel = movieModelAssembler.toModel(existingMovie, false);

        return movieRepresentationModel
                .getLink(IanaLinkRelations.SELF)
                .map(Link::getHref).map(href -> {
                    try {
                        return new URI(href);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(uri -> ResponseEntity.noContent().location(uri).build())
                .orElse(ResponseEntity.badRequest().body("Unable to update " + existingMovie + " partially"));
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        Optional<Movie> optional = movieRepository.findById(id);
        if (optional.isPresent()) {
            Movie movie = optional.get();
            for (Director director : movie.getDirectors()) {
                director.deleteMovie(movie);
            }
            movieRepository.deleteById(id);
        }

        return ResponseEntity.noContent().build();
    }
}
