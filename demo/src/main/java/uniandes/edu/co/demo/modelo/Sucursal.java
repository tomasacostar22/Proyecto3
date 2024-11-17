package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;



@Data
@Document(collection = "Sucursal")
public class Sucursal {
    @Id
    private String codigo;
    private String nombre;
    private String tamaño;
    private String ciudad;
    private List<Bodega> bodegas;


}
