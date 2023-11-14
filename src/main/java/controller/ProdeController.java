package controller;

import lombok.NoArgsConstructor;
import service.ProdeService;

import java.io.FileNotFoundException;
import java.util.List;

@NoArgsConstructor
public class ProdeController {

    private ProdeService prodeService = new ProdeService();

    public void juego(String archivoPronosticos, String archivoResultados) throws FileNotFoundException {
        List<String> mensaje = prodeService.juego(archivoPronosticos, archivoResultados);

        for (String msj : mensaje){
            System.out.println(msj);
        }
    }

}
