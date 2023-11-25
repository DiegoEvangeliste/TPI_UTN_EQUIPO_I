import controller.ProdeController;
import repository.ProdeRepository;
import service.ProdeService;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ProdeRepository prodeRepository = new ProdeRepository();
        ProdeService prodeService = new ProdeService(prodeRepository);
        ProdeController prodeController = new ProdeController(prodeService);

        try {
            prodeController.obtenerResultadoDelProde();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}