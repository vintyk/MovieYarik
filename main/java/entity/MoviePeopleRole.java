package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class MoviePeopleRole {
    private Long movieId;
    private Long peopleId;
    private Long roleId;

    public MoviePeopleRole(Long movieId, Long peopleId, Long roleId) {
        this.movieId = movieId;
        this.peopleId = peopleId;
        this.roleId = roleId;
    }
}