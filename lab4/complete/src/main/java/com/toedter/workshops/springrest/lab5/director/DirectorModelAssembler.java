package com.toedter.workshops.springrest.lab5.director;

import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder.jsonApiModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
class DirectorModelAssembler {

    private static final String MOVIES = "movies";

    public RepresentationModel<?> toModel(Director director, boolean withAffordances) {
        Link selfLink = linkTo(methodOn(DirectorController.class).findOne(director.getId())).withSelfRel();
        Link directorsLink = linkTo(methodOn(DirectorController.class).findDirectors(director.getId())).withRel("directors");

        String relationshipSelfLink = selfLink.getHref() + "/relationships/" + MOVIES;
        String relationshipRelatedLink = selfLink.getHref() + "/" + MOVIES;

        JsonApiModelBuilder builder = jsonApiModel()
                .model(director)
                .link(selfLink);

        builder = builder
                .relationship(MOVIES, director.getMovies())
                .relationship(MOVIES, relationshipSelfLink, relationshipRelatedLink, null);

        return builder.build();
    }

    public RepresentationModel<?> directorsToModel(Director director) {
        Link selfLink = linkTo(methodOn(DirectorController.class).findDirectors(director.getId())).withSelfRel();

        RepresentationModel<?> movies = CollectionModel.of(director.getMovies());
        movies.add(selfLink);

        return movies;
    }
}
