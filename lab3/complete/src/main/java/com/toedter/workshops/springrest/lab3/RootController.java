package com.toedter.workshops.springrest.lab3;/*
 * Copyright 2023 the original author or authors.
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

import com.toedter.workshops.springrest.lab3.movie.MovieController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = HAL_FORMS_JSON_VALUE)
public class RootController {

    public static final String MOVIES = "movies";
    public static final String DIRECTORS = "directors";

    @GetMapping
    ResponseEntity<RepresentationModel<?>> root() {

        RepresentationModel<?> resourceSupport = new RepresentationModel<>();

        resourceSupport.add(linkTo(methodOn(RootController.class).root()).withSelfRel());

        Link moviesLink = linkTo(MovieController.class).slash(MOVIES).withRel(MOVIES);
        Link templatedMoviesLink = Link.of(moviesLink.getHref() + "{?page,size}").withRel(MOVIES);

        resourceSupport.add(templatedMoviesLink);

        Link directorsLink = linkTo(MovieController.class).slash(DIRECTORS).withRel(DIRECTORS);
        Link templatedDirectorsLink = Link.of(directorsLink.getHref() + "{?page,size}").withRel(DIRECTORS);

        resourceSupport.add(templatedDirectorsLink);

        return ResponseEntity.ok(resourceSupport);
    }

}
