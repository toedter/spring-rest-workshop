package com.toedter.workshops.springrest.lab5.movie;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
}
