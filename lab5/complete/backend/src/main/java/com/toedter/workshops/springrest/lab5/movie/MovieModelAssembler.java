/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.toedter.workshops.springrest.lab5.movie;

import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder.jsonApiModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;

@Component
@Slf4j
class MovieModelAssembler {

    private static final String DIRECTORS = "directors";

    public RepresentationModel<?> toModel(Movie movie) {
        Link selfLink = linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel();
        Link directorsLink = linkTo(methodOn(MovieController.class).findDirectors(movie.getId())).withRel("directors");

        String relationshipSelfLink = selfLink.getHref() + "/relationships/" + DIRECTORS;
        String relationshipRelatedLink = selfLink.getHref() + "/" + DIRECTORS;

        final Affordance updatePartiallyAffordance =
                afford(methodOn(MovieController.class).updateMoviePartially(EntityModel.of(movie), movie.getId()));

        final Affordance deleteAffordance =
                afford(methodOn(MovieController.class).deleteMovie(movie.getId()));

        JsonApiModelBuilder builder = jsonApiModel()
                .model(movie)
                .link(selfLink.andAffordance(updatePartiallyAffordance).andAffordance(deleteAffordance));

        builder = builder
                .relationship(DIRECTORS, movie.getDirectors())
                .relationship(DIRECTORS, relationshipSelfLink, relationshipRelatedLink, null);

        return builder.build();
    }

    public RepresentationModel<?> directorsToModel(Movie movie) {
        Link selfLink = linkTo(methodOn(MovieController.class).findDirectors(movie.getId())).withSelfRel();

        return CollectionModel.of(movie.getDirectors(), selfLink);
    }
}