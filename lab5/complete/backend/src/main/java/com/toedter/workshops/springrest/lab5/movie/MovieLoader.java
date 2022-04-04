package com.toedter.workshops.springrest.lab5.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.workshops.springrest.lab5.director.Director;
import com.toedter.workshops.springrest.lab5.director.DirectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Slf4j
class MovieLoader {

    @Bean
    CommandLineRunner init(MovieRepository movieRepository, DirectorRepository directorRepository) {

        return args -> {
            String moviesJson;
            ObjectMapper mapper = new ObjectMapper();

            File file = ResourceUtils.getFile("classpath:static/movie-data/movies-250.json");

            moviesJson = readFile(file.getPath());
            JsonNode rootNode = mapper.readValue(moviesJson, JsonNode.class);

            JsonNode movies = rootNode.get("movies");
            int rating = 1;
            for (JsonNode movieNode : movies) {
                Movie movie = createMovie(rating++, movieNode);
                movieRepository.save(movie);

                String directors = movieNode.get("Director").asText();
                String[] directorList = directors.split(",");

                for (String directorName : directorList) {
                    Director director = directorRepository.findByName(directorName.trim());
                    if (director == null) {
                        director = new Director(directorName.trim());
                    }
                    log.info("adding movie \"" + movie.getTitle() + "\" to director \"" + directorName.trim() + "\".");
                    director.addMovie(movie);
                    directorRepository.save(director);
                    movie.addDirector(director);
                    movieRepository.save(movie);
                }
            }
        };
    }

    private Movie createMovie(int rank, JsonNode rootNode) {
        String title = rootNode.get("Title").asText();
        String imdbId = rootNode.get("imdbID").asText();

        long year = rootNode.get("Year").asLong();
        double imdbRating = rootNode.get("imdbRating").asDouble();

        String movieImage = "/movie-data/thumbs/" + imdbId + ".jpg";

        return new Movie(imdbId, title, year, imdbRating, rank, movieImage);
    }

    private String readFile(String path)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

}
