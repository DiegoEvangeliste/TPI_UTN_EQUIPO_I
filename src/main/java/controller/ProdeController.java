package controller;

import lombok.NoArgsConstructor;
import service.ProdeService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ProdeController {

    private final ProdeService prodeService;

    public ProdeController(ProdeService prodeService){
        this.prodeService = prodeService;
    }

    public void obtenerResultadoDelProde() throws IOException {
        prodeService.juego();
    }

}
