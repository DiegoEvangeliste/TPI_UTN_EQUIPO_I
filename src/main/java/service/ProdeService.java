package service;

import lombok.NoArgsConstructor;
import model.Participante;
import model.Partido;
import model.Pronostico;
import model.Ronda;
import repository.ProdeRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ProdeService{

    private ProdeRepository prodeRepository = new ProdeRepository();

    // Instanciamos las rondas y los pronosticos y devolvemos los puntajes de cada participante.
    public List<String> juego(String archivoPronosticos, String archivoResultados) throws FileNotFoundException {

        List<Participante> participantes= ProdeRepository.leerPronosticosDeParticipates(archivoPronosticos);
        List<Ronda> rondas =  ProdeRepository.leerResultados(archivoResultados);

        for(Ronda r: rondas){
            for(Partido p: r.getPartidos()){
                for(Participante participante: participantes){
                    for(Pronostico pronostico: participante.getListaDePronosticos()){
                        if(p.getId().equals(pronostico.getPartido().getId())){
                            if(p.resultado(pronostico.getEquipo()).equals(pronostico.getResutladoEnum())){
                                participante.sumarPunto();  //  Le sumamos un punto al participante
                            }
                        }
                    }
                }
            }
        }

        List<String> mensaje = new ArrayList<>();

        for (Participante participante : participantes){
            mensaje.add("El/la participante " + participante.getNombre() + " tiene " + participante.getPuntos());
        }

        return mensaje;
    }

}
