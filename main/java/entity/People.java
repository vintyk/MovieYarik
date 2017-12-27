package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class People {
    private Long id;
    private String namePeople;
    private String familyPeople;
    private Long dateOfBirthPeople;
    private Role role;

    public People(Long id, String namePeople, String familyPeople, Long dateOfBirthPeople, Role role) {
        this.id = id;
        this.namePeople = namePeople;
        this.familyPeople = familyPeople;
        this.dateOfBirthPeople = dateOfBirthPeople;
        this.role = role;
    }
}
