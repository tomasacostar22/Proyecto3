package uniandes.edu.co.demo.modelo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.Date;

@Data
@Document(collection = "Productos")
public class Producto {

    @Id
    private String codigoBarras;
    private String nombre;
    private String presentacion;
    private int cantidad;
    private String unidadMedida;
    private String codigoCategoria; 
    private Date fechaExpiracion;
    private double precio;
}
