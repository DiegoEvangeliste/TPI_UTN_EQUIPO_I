package model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Equipo {

    private String nombre;
    private String descripcion;

    public Equipo(String nombre){
            this.nombre = nombre;
            this.descripcion = "Seleccion";
    }

}
