package model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participante {

    private String Nombre;
    private List<Pronostico> listaDePronosticos;
    private int puntos;
    private boolean acertoTodosLosPartidosXFase = true;

    public boolean acertoTodosLosPartidosXFase() {
        return acertoTodosLosPartidosXFase;
    }

    public void setAcertoTodosLosPartidosXFase(boolean acertoTodosLosPartidosXFase) {
        this.acertoTodosLosPartidosXFase = acertoTodosLosPartidosXFase;
    }

    public void puntosExtraXRonda(){
        this.puntos += 5;
    }
    public void puntosExtraXFase(){
        this.puntos += 10;
    }
}
