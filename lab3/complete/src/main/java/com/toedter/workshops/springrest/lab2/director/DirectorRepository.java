package com.toedter.workshops.springrest.lab2.director;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface DirectorRepository extends PagingAndSortingRepository<Director, Long> {
    Director findByName(String name);
}
