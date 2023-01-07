package com.toedter.workshops.springrest.lab2.director;

public interface DirectorRepository {
    Director findByName(String name);
}
