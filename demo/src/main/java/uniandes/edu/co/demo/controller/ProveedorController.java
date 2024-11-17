package uniandes.edu.co.demo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.demo.modelo.Proveedor;
import uniandes.edu.co.demo.repository.ProveedorRepository;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    private ProveedorRepository proveedorRepository;

    // Crear un nuevo proveedor
    @PostMapping("/crear")
    public ResponseEntity<String> crearProveedor(@RequestBody Proveedor proveedor) {
        try {
            proveedorRepository.crearProveedor(proveedor);
            return ResponseEntity.ok("Proveedor creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar un proveedor
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarProveedor(@RequestBody Proveedor proveedor) {
        try {
            proveedorRepository.actualizarProveedor(proveedor);
            return ResponseEntity.ok("Proveedor actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
