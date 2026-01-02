package com.toedter.workshops.springrest.lab2.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.toedter.workshops.springrest.lab2.director.Director;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Relation(collectionRelation = "movies")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String title;
    private Long year;
    private String imdbId;
    private Double rating;
    private Integer rank;
    @JsonIgnore
    private String thumb;

    @ManyToMany(mappedBy = "movies", fetch = FetchType.EAGER)
    private List<Director> directors = new ArrayList<>();

    public Movie(String imdbId, String title, Long year, Double rating, Integer rank, String thumb) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.rank = rank;
        this.thumb = thumb;
    }

    public void addDirector(Director director) {
        directors.add(director);
    }
}
