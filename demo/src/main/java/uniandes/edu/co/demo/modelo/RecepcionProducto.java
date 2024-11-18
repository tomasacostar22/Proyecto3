package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.util.Date;

@Data
@Document(collection = "recepcionDeProductos")
public class RecepcionProducto {
    @Id
    private String id; 
    private Date fechaHora;
    private String codigoBodega; 
}
