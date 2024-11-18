package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniandes.edu.co.demo.dto.OrdenDeCompraRequest;
import uniandes.edu.co.demo.modelo.OrdenDeCompra;
import uniandes.edu.co.demo.repository.OrdenDeCompraRepository;
import uniandes.edu.co.demo.services.OrdenDeCompraServicio;

@RestController
@RequestMapping("/ordenDeCompra")
public class OrdenDeCompraController {

    @Autowired
    private OrdenDeCompraServicio ordenDeCompraServicio;

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    // Crear una nueva orden de compra
    @PostMapping("/crear")
    public ResponseEntity<String> crearOrdenDeCompra(@RequestBody OrdenDeCompraRequest ordenDeCompraRequest) {

        try {
            ordenDeCompraServicio.crearOrdenDeCompra(ordenDeCompraRequest);
            return ResponseEntity.ok("Orden de compra creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Encontrar una orden de compra por id
    @GetMapping("/leer/{id}")
    public ResponseEntity<OrdenDeCompra> encontrarOrdenDeCompraPorId(@PathVariable String id) {
        try {
            OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.encontrarOrdenDeCompraPorId(id);
            return ResponseEntity.ok(ordenDeCompra);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
}
