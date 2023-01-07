package com.toedter.workshops.springrest.lab3.movie;

import com.toedter.workshops.springrest.lab3.director.Director;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
class MovieModelAssembler {

    private static final String DIRECTORS = "directors";

    public EntityModel<Movie> toModel(Movie movie) {
        Link selfLink = linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel();
        Link directorsLink = linkTo(methodOn(MovieController.class).findDirectors(movie.getId())).withRel(DIRECTORS);

        return EntityModel.of(movie, selfLink, directorsLink);
    }

    public CollectionModel<Director> directorsToModel(Movie movie) {
        Link selfLink = linkTo(methodOn(MovieController.class).findDirectors(movie.getId())).withSelfRel();

        return CollectionModel.of(movie.getDirectors(), selfLink);
    }
}
