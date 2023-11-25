package model;


import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prode {

    private List<Fase> fases;
    private List<Participante> participantes;

}
