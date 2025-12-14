package com.toedter.workshops.springrest.lab2;

import com.toedter.workshops.springrest.lab2.movie.Movie;
import com.toedter.workshops.springrest.lab2.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class MovieRepositoryIntegrationTest {

//    @Autowired
//    MovieRepository movieRepository;

    @Test
    void shouldFindAllMovies() {
//        Iterable<Movie> movies = movieRepository.findAll();
//        assertThat(movies, is(not(emptyIterable())));
    }

    @Test
    void shouldCreateNewMovie() {
//        long before = movieRepository.count();
//
//        Movie movie = movieRepository.save(createMovie());
//
//        Iterable<Movie> result = movieRepository.findAll();
//        assertThat(result, is(iterableWithSize((int) (before + 1))));
    }

    public static Movie createMovie() {
        return new Movie("imdbid", "New Movie", 2022L, 7.0, 1000, "thumb");
    }
}
