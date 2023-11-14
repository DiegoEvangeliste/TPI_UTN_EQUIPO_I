package model;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ronda {

    private String numero;
    private List<Partido> partidos;

    /*
    public int puntos(){
        return 0;
    }
    */

}
