package repository;

import Config.DBConnectionConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProdeRepository {
    public  Fase leerFasesDeDB() throws IOException {
        Fase fase =  null;
        String numeroFase = null;
        List<Ronda> listaRondas = leerResultadosDeDB();
        try{
            Connection cn = DBConnectionConfig.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM fases");
            while(rs.next()){
                numeroFase = rs.getString("numero");
                fase = new Fase(numeroFase, listaRondas);
            }
            cn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  fase;
    }

    public  List<Participante> leerPronosticoDeParticipatesDeDB(){
        List<Participante> listaDeParticipantes = new ArrayList<>();
        List<Pronostico> listaDePronosticos = null;
        Participante participante = null;
        try{
            Connection cn = DBConnectionConfig.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT Participantes.nombre, Partidos.idPartido, Equipo1.nombre AS equipo1, Equipo2.nombre AS equipo2, Pronosticos.resultado, Equipos.nombre AS equipoSeleccionado\n" +
                    "FROM Participantes\n" +
                    "JOIN Pronosticos ON Participantes.idParticipantes = Pronosticos.Participantes_idParticipantes\n" +
                    "JOIN Partidos ON Pronosticos.Partidos_idPartido = Partidos.idPartido\n" +
                    "JOIN Equipos AS Equipo1 ON Partidos.equipo1_idEquipo = Equipo1.idEquipo\n" +
                    "JOIN Equipos AS Equipo2 ON Partidos.equipo2_idEquipo = Equipo2.idEquipo\n" +
                    "JOIN Equipos ON Pronosticos.Equipos_idEquipo = Equipos.idEquipo\n" +
                    "ORDER BY Participantes.nombre;");
            String participanteActual = null;
            String participanteAnterior = "";
            while(rs.next()){
                participanteActual = rs.getString("nombre");
                if(!participanteActual.equals(participanteAnterior)){
                    if (participante != null) {
                        participante.setListaDePronosticos(listaDePronosticos);
                        listaDeParticipantes.add(participante);
                    }
                    participante = new Participante();
                    participante.setNombre(rs.getString("nombre"));
                    listaDePronosticos = new ArrayList<>();
                }
                int idPartido = rs.getInt("idPartido");
                Equipo equipo1 = new Equipo(rs.getString("equipo1"));
                Equipo equipo2 = new Equipo(rs.getString("equipo2"));
                Partido partido = new Partido(idPartido, equipo1, equipo2);
                Equipo equipoSeleccionado = new Equipo(rs.getString("equipoSeleccionado"));
                ResultadoEnum resultado = ResultadoEnum.valueOf(rs.getString("resultado").toUpperCase());
                Pronostico pronostico = new Pronostico(partido, equipoSeleccionado, resultado);
                listaDePronosticos.add(pronostico);
                participanteAnterior = participanteActual;
            }
            if(participante != null){
                participante.setListaDePronosticos(listaDePronosticos);
                listaDeParticipantes.add(participante);
            }
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDeParticipantes;
    }

    public  List<Ronda> leerResultadosDeDB(){
        List<Ronda> rondas = new ArrayList<>();
        List<Partido> partidos = new ArrayList<>();
        Ronda ronda = null;
        try{
            Connection cn = DBConnectionConfig.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT Rondas.numero AS nombre_ronda, P.idPartido, E1.nombre AS equipo1_nombre, E2.nombre As equipo2_nombre, R1.goles AS goles_equipo1, R2.goles AS goles_equipo2, R1.resultado \n" +
                    "AS resultado_equipo1, R2.resultado AS resultado_equipo2\n" +
                    "FROM partidos P\n" +
                    "JOIN resultados R1 ON P.idPartido = R1.Partidos_idPartido AND R1.Equipos_idEquipo = P.equipo1_idEquipo\n" +
                    "JOIN resultados R2 ON P.idPartido = R2.Partidos_idPartido AND R2.Equipos_idEquipo = P.equipo2_idEquipo\n" +
                    "JOIN equipos E1 ON P.equipo1_idEquipo = E1.idEquipo\n" +
                    "JOIN equipos E2 ON P.equipo2_idEquipo = E2.idEquipo\n" +
                    "JOIN rondas Rondas ON P.rondas_id = Rondas.id\n" +
                    "GROUP BY P.idPartido, Rondas.numero;");
            String nombreRondaActual = null;
            String nombreRondaAnterior = "";
            while(rs.next()){
                nombreRondaActual= rs.getString("nombre_ronda");
                if(!nombreRondaActual.equals(nombreRondaAnterior)){
                    if (ronda != null) {
                        ronda.setPartidos(partidos);
                        rondas.add(ronda);
                    }
                    ronda = new Ronda();
                    ronda.setNumero(rs.getString("nombre_ronda"));
                    partidos = new ArrayList<>();
                }
                int idPartido = rs.getInt("idPartido");
                Equipo equipo1 = new Equipo(rs.getString("equipo1_nombre"));
                Equipo equipo2 = new Equipo(rs.getString("equipo2_nombre"));
                int golesEquipo1 = rs.getInt("goles_equipo1");
                int golesEquipo2 = rs.getInt("goles_equipo2");
                Partido p = new Partido(idPartido, equipo1, equipo2, golesEquipo1, golesEquipo2);
                partidos.add(p);
                nombreRondaAnterior = nombreRondaActual;
            }
            if(ronda != null){
                ronda.setPartidos(partidos);
                rondas.add(ronda);
            }
            cn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  rondas;
    }


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
                pronostico.setResultadoEnum(ResultadoEnum.valueOf(atributos[3]));
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
