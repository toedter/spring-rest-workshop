package com.toedter.workshops.springrest.lab4.director;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DirectorRepository extends CrudRepository<Director, Long>, PagingAndSortingRepository<Director, Long> {
    Director findByName(String name);
}
