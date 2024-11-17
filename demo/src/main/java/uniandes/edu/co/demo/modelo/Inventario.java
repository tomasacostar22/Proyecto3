package uniandes.edu.co.demo.modelo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "inventarios")
public class Inventario {
    @Id
    private String id; // Puedes generar un ID único para MongoDB
    private String codigoBodega; // Relación lógica con Bodega
    private String codigoBarras; // Relación lógica con Producto
    private int cantidad;
    private int nivelMinimo;
    private double costoBodega;
}
