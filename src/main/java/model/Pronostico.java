package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pronostico {

    private Partido partido;
    private Equipo equipo;
    private ResutladoEnum resutladoEnum;

}
