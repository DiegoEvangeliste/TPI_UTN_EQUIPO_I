package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partido {

    private Integer id;
    private Equipo equipo1;
    private Equipo equipo2;
    private int golesEquipo1;
    private int golesEquipo2;

    public ResultadoEnum resultado(Equipo e){
        ResultadoEnum resultadoEnum = null;
        if(e.getNombre().equals(equipo1.getNombre())){
            if(this.golesEquipo1 > golesEquipo2){
                resultadoEnum = ResultadoEnum.GANADOR;
            } else if (this.golesEquipo1 == this.golesEquipo2) {
                resultadoEnum = ResultadoEnum.EMPATE;
            }else{
                resultadoEnum = ResultadoEnum.PERDEDOR;
            }
        } else if (e.getNombre().equals(equipo2.getNombre())) {
            if(this.golesEquipo2 > this.golesEquipo1){
                resultadoEnum = ResultadoEnum.GANADOR;
            } else if (this.golesEquipo1 == this.golesEquipo2) {
                resultadoEnum = ResultadoEnum.EMPATE;
            }else{
                resultadoEnum = ResultadoEnum.PERDEDOR;
            }
        }
        return resultadoEnum;
    }
    public Partido(int id,Equipo equipo1, Equipo equipo2){
        this.id = id;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }

}
