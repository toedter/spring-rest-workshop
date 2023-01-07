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

package com.toedter.workshops.springrest.lab3.movie;

import com.toedter.workshops.springrest.lab3.director.Director;
import com.toedter.workshops.springrest.lab3.director.DirectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
