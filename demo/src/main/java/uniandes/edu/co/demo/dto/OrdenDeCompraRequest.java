package uniandes.edu.co.demo.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;
import uniandes.edu.co.demo.modelo.DetalleOrden;

@Data
public class OrdenDeCompraRequest {

    private String codigo;
    private Date fechaEntrega;
    private String nitProveedor; 
    private String codigoSucursal; 
    private List<DetalleOrden> detalleOrden;

}
