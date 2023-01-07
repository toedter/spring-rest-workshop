package com.toedter.workshops.springrest.lab5.director;

import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import com.toedter.workshops.springrest.lab5.movie.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder.jsonApiModel;
import static com.toedter.spring.hateoas.jsonapi.MediaTypes.JSON_API_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api", produces = JSON_API_VALUE)
public class DirectorController {

    private final DirectorRepository directorRepository;
    private final DirectorModelAssembler directorModelAssembler;

    DirectorController(MovieRepository movieRepository, DirectorRepository directorRepository,
                       DirectorModelAssembler directorModelAssembler) {
        this.directorRepository = directorRepository;
        this.directorModelAssembler = directorModelAssembler;
    }

    @GetMapping("/directors")
    public ResponseEntity<RepresentationModel<?>> findAll(
            @RequestParam(value = "page[number]", defaultValue = "0", required = false) int page,
            @RequestParam(value = "page[size]", defaultValue = "10", required = false) int size) {

        final PageRequest pageRequest = PageRequest.of(page, size);

        final Page<Director> pagedResult = directorRepository.findAll(pageRequest);

        List<? extends RepresentationModel<?>> movieResources =
                StreamSupport.stream(pagedResult.spliterator(), false)
                        .map((Director director) -> directorModelAssembler.toModel(director, false))
                        .collect(Collectors.toList());

        Link selfLink = linkTo(DirectorController.class).slash("directors?"
                + "page=" + pagedResult.getNumber()
                + "&size=" + pagedResult.getSize()).withSelfRel();

        PagedModel.PageMetadata pageMetadata =
                new PagedModel.PageMetadata(
                        pagedResult.getSize(),
                        pagedResult.getNumber(),
                        pagedResult.getTotalElements(),
                        pagedResult.getTotalPages());

        final PagedModel<? extends RepresentationModel<?>> pagedModel =
                PagedModel.of(movieResources, pageMetadata);

        String pageLinksBase =
                linkTo(DirectorController.class).slash("directors").withSelfRel().getHref();

        final JsonApiModelBuilder jsonApiModelBuilder =
                jsonApiModel().model(pagedModel).link(selfLink).pageLinks(pageLinksBase);

        for (Director director : pagedResult.getContent()) {
            jsonApiModelBuilder.included(director.getMovies());
        }

        return ResponseEntity.ok(jsonApiModelBuilder.build());
    }


    @GetMapping("/directors/{id}")
    public ResponseEntity<? extends RepresentationModel<?>> findOne(
            @PathVariable Long id) {

        return directorRepository.findById(id)
                .map((Director director) -> directorModelAssembler.toModel(director, true))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/directors/{id}/movies")
    public ResponseEntity<? extends RepresentationModel<?>> findDirectors(@PathVariable Long id) {

        return directorRepository.findById(id)
                .map(directorModelAssembler::directorsToModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
