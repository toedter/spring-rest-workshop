package com.toedter.workshops.springrest.lab2.director;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Director findByName(String name);
}
