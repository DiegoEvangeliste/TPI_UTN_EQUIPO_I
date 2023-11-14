import controller.ProdeController;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        ProdeController prodeController = new ProdeController();
        try{
            prodeController.juego("/home/diego/Documentos/Programacion/TPI_UTN_EQUIPO_I/src/main/resources/pronosticos.cvs",
                        "/home/diego/Documentos/Programacion/TPI_UTN_EQUIPO_I/src/main/resources/resultados.cvs");
        }
        catch(FileNotFoundException e){
            new RuntimeException(e);

            System.out.print("No se pudo leer algun archivo de texto");
        }
    }
}