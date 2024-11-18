package uniandes.edu.co.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.demo.modelo.Proveedor;
import uniandes.edu.co.demo.repository.ProveedorRepository;




@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
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
    @PostMapping("/actualizar")
    public ResponseEntity<String> actualizarProveedor(@RequestBody Proveedor proveedor) {
        try {
            proveedorRepository.actualizarProveedor(proveedor);
            return ResponseEntity.ok("Proveedor actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
