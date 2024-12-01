package uniandes.edu.co.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.services.ProductoServicio;
import uniandes.edu.co.demo.repository.ProductoRepositoryCustom;

@RestController()
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private ProductoRepositoryCustom productoRepositoryCustom;
    // Crear un nuevo producto
    @PostMapping("/crear")
    public ResponseEntity<String> crearProducto(@RequestBody Producto producto) {
        try {
            productoServicio.crearProducto(producto);
            return ResponseEntity.ok("Producto creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar un producto
    @PutMapping("/actualizar/{codigoBarrasProductoViejo}")
    public ResponseEntity<String> actualizarProducto(@PathVariable String codigoBarrasProductoViejo, @RequestBody Producto productoNuevo) {
        try {
            productoServicio.actualizarProducto(codigoBarrasProductoViejo, productoNuevo);
            return ResponseEntity.ok("Producto actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filtrar/{menor}/{mayor}/{fecha}/{sucursal}/{categoria}/{mayorQue}")
    public ResponseEntity<List<Document>> filtrarProductos(
        @PathVariable int menor,
        @PathVariable int mayor,
        @PathVariable String fecha,
        @PathVariable String sucursal,
        @PathVariable String categoria,
        @PathVariable boolean mayorQue
    ) {
        // Log para depurar valores recibidos
        System.out.println("Valores recibidos: ");
        System.out.println("menor = " + menor);
        System.out.println("mayor = " + mayor);
        System.out.println("fecha = " + fecha);
        System.out.println("sucursal = " + sucursal);
        System.out.println("categoria = " + categoria);
        System.out.println("mayorQue = " + mayorQue);
    
        try {
            // Validaciones
            if (menor > mayor) {
                return ResponseEntity.badRequest().body(List.of(
                    new Document("error", "El precio menor no puede ser mayor que el precio mayor.")
                ));
            }
            LocalDate.parse(fecha); // Validar formato de fecha
    
            // Log antes de ejecutar el pipeline
            System.out.println("Ejecutando consulta con mayorQue = " + mayorQue);
    
            List<Document> productos = mayorQue
                ? productoRepositoryCustom.filtrarProductosMayor(menor, 150000, fecha, sucursal, categoria)
                : productoRepositoryCustom.filtrarProductosMenor(menor, 150000, fecha, sucursal, categoria);
    
            // Log del resultado obtenido
            System.out.println("Cantidad de productos encontrados: " + productos.size());
    
            return ResponseEntity.ok(productos);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(List.of(
                new Document("error", "Formato de fecha inválido. Use YYYY-MM-DD.")
            ));
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(List.of(
                new Document("error", e.getMessage())
            ));
        }
    }
    

}
        
    

