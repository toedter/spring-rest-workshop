package com.toedter.workshops.springrest.lab2.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
