package uniandes.edu.co.demo.modelo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "Proveedores")
public class Proveedor {
    @Id
    private String nit;
    private String nombre;
    private String direccion;
    private String nombreContacto;
    private String telefono;
}
