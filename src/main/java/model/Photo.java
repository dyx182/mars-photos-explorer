package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Photo {

    private int id;
    private int sol;
    private String camera;
    private String img_src;
    private Rover rover;


}
