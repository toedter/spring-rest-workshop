package com.toedter.workshops.springrest.lab2;

import com.toedter.workshops.springrest.lab2.movie.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieRepositoryIntegrationTest {

//    @Autowired
//    MovieRepository movieRepository;

    @Test
    public void shouldFindsAllMovies() {
//        Iterable<Movie> movies = movieRepository.findAll();
//        assertThat(movies, is(not(emptyIterable())));
    }

    @Test
    public void shouldCreatesNewMovie() {
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
