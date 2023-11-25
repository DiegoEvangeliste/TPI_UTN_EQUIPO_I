package service;

import lombok.NoArgsConstructor;
import model.*;
import repository.ProdeRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProdeService{

    //Aca inyectamos la dependencias.
    private final ProdeRepository prodeRepository;

    public ProdeService (ProdeRepository prodeRepository) {
        this.prodeRepository = prodeRepository;
    }

    public void juego() throws IOException {
        Fase fase = prodeRepository.leerFasesDeDB();
        List<Participante> juegoParticipantes = prodeRepository.leerPronosticoDeParticipatesDeDB();
        System.out.println("Fase : " +  fase.getNumero());
        for (Ronda r : fase.getRondas()) {
            System.out.println("Ronda : " + r.getNumero());
            int partidosXRonda = r.getPartidos().size();
            //Esto es para que solo me muestre los partidos una sola vez
            for (Partido partido : r.getPartidos()) {
                System.out.println(partido.getEquipo1().getNombre() + " vs " + partido.getEquipo2().getNombre());
            }
            for (Participante participante : juegoParticipantes) {
                int aciertosXRonda = 0;
                for (Partido partido : r.getPartidos()) {
                    for (Pronostico pronostico : participante.getListaDePronosticos()) {
                        if (pronostico.getPartido().getId() == partido.getId()) {
                            pronostico.setPartido(partido);
                            if (pronostico.esAcertado()) {
                                int puntos = pronostico.puntos() + participante.getPuntos();
                                participante.setPuntos(puntos);
                                aciertosXRonda++;
                            } else{
                                participante.setAcertoTodosLosPartidosXFase(false);
                            }
                        }
                    }
                }
                System.out.println("Participante " + participante.getNombre() + " acerto " + aciertosXRonda + " de " + partidosXRonda + " partidos de la ronda " + r.getNumero());
                if(aciertosXRonda == partidosXRonda){
                    System.out.println("El participante " + participante.getNombre() + " obtiene 5 puntos extras por acertar todos los partidos de una Ronda");
                    participante.puntosExtraXRonda();
                }

            }
        }
        for (Participante participante : juegoParticipantes) {
            if(participante.acertoTodosLosPartidosXFase()){
                System.out.println("El participante " + participante.getNombre() + " obtiene 10 puntos extras por acertar todos los partidos de una Fase");
                participante.puntosExtraXFase();
            }
            System.out.println("Participante " + participante.getNombre() + " obtuvo un puntaje final de : " + participante.getPuntos() + " puntos!"
            );
        }
    }

}
