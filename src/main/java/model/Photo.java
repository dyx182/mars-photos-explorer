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
    private String cameraName;
    private String img_src;
    private String rover;


}
