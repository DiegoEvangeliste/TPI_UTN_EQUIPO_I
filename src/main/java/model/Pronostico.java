package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pronostico {

    private Partido partido;
    private Equipo equipo;
    private ResultadoEnum resultadoEnum;

    public Boolean esAcertado(){
        if(this.resultadoEnum.equals(this.partido.resultado(this.equipo))){
            return true;
        }else{
            return false;
        }
    }
    public int puntos() {
        // this.resultado -> pred
        ResultadoEnum resultadoReal = this.partido.resultado(this.equipo);
        if (this.resultadoEnum.equals(resultadoReal)) {
            return 1;
        } else {
            return 0;
        }
    }
}
