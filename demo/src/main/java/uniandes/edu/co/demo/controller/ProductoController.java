package uniandes.edu.co.demo.controller;

import java.util.List;

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

     @GetMapping("/filtrar")
    public ResponseEntity<List<?>> filtrarProductos(
        @RequestParam int menor,
        @RequestParam int mayor,
        @RequestParam String fecha,
        @RequestParam String sucursal,
        @RequestParam String categoria
    ) {
        try {
            List<?> productos = productoRepositoryCustom.filtrarProductos(menor, mayor, fecha, sucursal, categoria);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of("Error: " + e.getMessage()));
        }
    }
}
        
    

