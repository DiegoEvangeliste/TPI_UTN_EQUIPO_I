package model;


import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Prode {

    private List<Ronda> rondas;
    private List<Participante> participantes;

}
