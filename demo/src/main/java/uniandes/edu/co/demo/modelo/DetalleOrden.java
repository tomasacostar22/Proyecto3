package uniandes.edu.co.demo.modelo;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "DetalleOrden")
public class DetalleOrden {

    private String codigo_barras_producto;
    private int cantidad;
    private int precio_unitario;
    
}
