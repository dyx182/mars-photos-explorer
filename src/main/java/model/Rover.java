package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rover {
    CURIOSITY("Curiosity"),
    PERSEVERANCE("Perseverance"),
    OPPORTUNITY("Opportunity"),
    SPIRIT("Spirit");

    private final String name;

}
