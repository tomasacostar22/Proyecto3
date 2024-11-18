package uniandes.edu.co.demo.modelo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "OrdenesDeCompra")
public class OrdenDeCompra {
    @Id
    private String codigo; 
    private Date fechaCreacion;
    private Date fechaEntrega;
    private EstadoOrden estado;
    private String nitProveedor; 
    private String codigoSucursal; 
    private RecepcionProducto recepcionProducto;
    private List<DetalleOrden> detalleOrden;

}
