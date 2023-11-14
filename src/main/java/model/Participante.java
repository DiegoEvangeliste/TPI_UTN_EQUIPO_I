package model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Participante {

    private String Nombre;
    private List<Pronostico> listaDePronosticos;
    private int puntos = 0;


    public void sumarPunto(){
        this.puntos++;
    }

}
