package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class Movie {
    private Long id;
    private String nameMovie;
    private Long year;
    private People people;
    private Review review;
    private Long rank;
    private Genre genre;
    private Country country;

    public Movie(Long id, String nameMovie, Long year, People people, Review review, Long rank, Genre genre, Country country) {
        this.id = id;
        this.nameMovie = nameMovie;
        this.year = year;
        this.people = people;
        this.review = review;
        this.rank = rank;
        this.genre = genre;
        this.country = country;
    }
}
