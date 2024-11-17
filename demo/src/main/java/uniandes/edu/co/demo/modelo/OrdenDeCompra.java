package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "ordenesDeCompra")
public class OrdenDeCompra {
    @Id
    private String id; 
    private String codigoBarras; 
    private int cantidad;
    private String estado;
    private String nitProveedor; 
    private String codigoSucursal; 
    private RecepcionProducto recepcionProducto;
}
