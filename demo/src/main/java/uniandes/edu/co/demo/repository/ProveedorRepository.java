package uniandes.edu.co.demo.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.modelo.Proveedor;

public interface ProveedorRepository extends MongoRepository<Proveedor, String> {

    // Crear un proveedor
    default void crearProveedor(Proveedor proveedor) {
        // Verificar si ya existe el proveedor
        if (encontrarProveedorPorNit(proveedor.getNit()) != null) {
            throw new RuntimeException("Ya existe un proveedor con el nit: " + proveedor.getNit());
        }
        save(proveedor);
    }

    // Encontrar proveedor por nit
    @Query("{ 'nit' : ?0 }") 
    Proveedor encontrarProveedorPorNit(String nit);

    // Actualizar proveedor
    default void actualizarProveedor(Proveedor proveedor) {
        // Verificar si el proveedor existe
        if (encontrarProveedorPorNit(proveedor.getNit()) == null) {
            throw new RuntimeException("No existe un proveedor con el nit: " + proveedor.getNit());
        }
        save(proveedor);
    }
    
}
