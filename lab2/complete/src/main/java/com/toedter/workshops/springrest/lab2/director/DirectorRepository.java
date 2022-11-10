package com.toedter.workshops.springrest.lab2.director;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DirectorRepository extends CrudRepository<Director, Long>, PagingAndSortingRepository<Director, Long> {
    Director findByName(String name);
}
