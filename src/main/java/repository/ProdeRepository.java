package repository;

import lombok.NoArgsConstructor;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
public class ProdeRepository {

    // Lee un CSV e inicializa los pronotsticos de los participantes
    public static List<Participante> leerPronosticosDeParticipates(String archivoPronosticos) throws FileNotFoundException {

        List<Participante> participantes = new ArrayList<>();
        Scanner scanner = new Scanner(new File(archivoPronosticos));
        Participante participanteActual = null;

        while(scanner.hasNextLine()){
            String linea = scanner.nextLine();
            String[] atributos =  linea.split(",");
            if(atributos[0].equals("Participante")){
                if(participanteActual != null){
                    participantes.add(participanteActual);
                }
                participanteActual = new Participante();
                participanteActual.setNombre(atributos[1]);
                List<Pronostico> pronosticoXParticipante = new ArrayList<>();
                participanteActual.setListaDePronosticos(pronosticoXParticipante);
            } else if ((atributos[0].equals("Pronostico")) && participanteActual != null ) {
                Partido partido = new Partido();
                partido.setId(Integer.parseInt(atributos[1]));
                Pronostico pronostico = new Pronostico();
                Equipo equipo  = new Equipo(String.valueOf(atributos[2]), "Seleccion");
                pronostico.setPartido(partido);
                pronostico.setEquipo(equipo);
                pronostico.setResutladoEnum(ResutladoEnum.valueOf(atributos[3]));
                participanteActual.getListaDePronosticos().add(pronostico);
            }
        }

        if(participanteActual != null){
            participantes.add(participanteActual);
        }

        return participantes;
    }

    // Lee un CSV e inicializa los resultados de los partidos
    public static List<Ronda> leerResultados(String archivoResultados) throws FileNotFoundException {

        List<Ronda> listaDeRondas =  new ArrayList<>();
        Scanner scanner = new Scanner(new File(archivoResultados));
        Ronda rondaActual = null;

        while(scanner.hasNextLine()){
            String linea = scanner.nextLine();
            String[] atributos = linea.split(",");
            if(atributos[0].equals("Ronda")){
                if(rondaActual != null){
                    listaDeRondas.add(rondaActual);
                }
                rondaActual = new Ronda();
                List<Partido> listaDePartidosXRonda = new ArrayList<>();
                rondaActual.setNumero(String.valueOf(atributos[1]));
                rondaActual.setPartidos(listaDePartidosXRonda);
            } else if((atributos[0].equals("Partido")) && rondaActual != null){
                Equipo equipo1 = new Equipo(String.valueOf(atributos[2]), "Seleccion");
                Equipo equipo2 =  new Equipo(String.valueOf(atributos[5]), "Seleccion");
                Partido partido = new Partido(Integer.parseInt(atributos[1]),equipo1,equipo2,Integer.parseInt(atributos[3]), Integer.parseInt(atributos[4]));
                rondaActual.getPartidos().add(partido);
            }
        }

        if(rondaActual != null){
            listaDeRondas.add(rondaActual);
        }

        return listaDeRondas;
    }

}
