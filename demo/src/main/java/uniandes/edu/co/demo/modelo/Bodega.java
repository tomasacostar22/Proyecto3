package uniandes.edu.co.demo.modelo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "bodegas")
public class Bodega {
    @Id
    private String codigo;
    private String nombre;
    private String tamaño;
}