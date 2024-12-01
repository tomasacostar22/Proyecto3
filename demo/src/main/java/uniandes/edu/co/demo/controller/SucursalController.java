package uniandes.edu.co.demo.controller;

import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.demo.modelo.Bodega;
import uniandes.edu.co.demo.modelo.Sucursal;
import uniandes.edu.co.demo.repository.SucursalRepository;
import uniandes.edu.co.demo.repository.SucursalRepositoryCustom;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private SucursalRepositoryCustom sucursalRepositoryCustom;

    // Crear una nueva sucursal
    @PostMapping("/crear")
    public ResponseEntity<String> crearSucursal(@RequestBody Sucursal sucursal) {
        try {
            sucursalRepository.crearSucursal(sucursal);
            return ResponseEntity.ok("Sucursal creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Agregar una bodega a una sucursal
    @PostMapping("/agregarBodega/{sucursalId}")
    public ResponseEntity<String> agregarBodegaASucursal(@RequestBody Bodega bodega, @PathVariable String sucursalId) {
        try {
            sucursalRepository.agregarBodegaASucursal(sucursalId, bodega);
            return ResponseEntity.ok("Bodega agregada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar una bodega
    @GetMapping("/eliminarBodega/{bodegaId}")
    public ResponseEntity<String> eliminarBodega(@PathVariable String bodegaId) {
        try {
            sucursalRepository.eliminarBodega(bodegaId);
            return ResponseEntity.ok("Bodega eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/reporte/{nombreSucursal}")
    public ResponseEntity<List<Document>> generarReporte(@PathVariable String nombreSucursal) {
        try {
            List<Document> reporte = sucursalRepositoryCustom.generarReportePorSucursal(nombreSucursal);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of(new Document("error", e.getMessage())));
        }
    }
    
        
}
