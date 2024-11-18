package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.services.ProductoServicio;

@RestController()
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoServicio productoServicio;

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
        
    
}
