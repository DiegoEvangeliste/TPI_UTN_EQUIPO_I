package model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Partido {

    private Integer id;
    private Equipo equipo1;
    private Equipo equipo2;
    private int golesEquipo1;
    private int golesEquipo2;

    public ResutladoEnum resultado(Equipo e){
        ResutladoEnum resutladoEnum = null;
        if(e.getNombre().equals(equipo1.getNombre())){
            if(this.golesEquipo1 > golesEquipo2){
                resutladoEnum = ResutladoEnum.GANADOR;
            } else if (this.golesEquipo1 == this.golesEquipo2) {
                resutladoEnum = ResutladoEnum.EMPATE;
            }else{
                resutladoEnum = ResutladoEnum.PERDEDOR;
            }
        } else if (e.getNombre().equals(equipo2.getNombre())) {
            if(this.golesEquipo2 > this.golesEquipo1){
                resutladoEnum = ResutladoEnum.GANADOR;
            } else if (this.golesEquipo1 == this.golesEquipo2) {
                resutladoEnum = ResutladoEnum.EMPATE;
            }else{
                resutladoEnum = ResutladoEnum.PERDEDOR;
            }
        }
        return  resutladoEnum;
    }

}
