package uniandes.edu.co.demo.repository;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.demo.modelo.Bodega;
import uniandes.edu.co.demo.modelo.Sucursal;

public interface SucursalRepository extends MongoRepository<Sucursal, String> {

    // Crear una sucursal
    default void crearSucursal(Sucursal sucursal) {
        // Verificar si ya existe la sucursal
        if (encontrarSucursalPorCodigo(sucursal.getCodigo()) != null) {
            throw new RuntimeException("Ya existe una sucursal con el id: " + sucursal.getCodigo());
        }
        save(sucursal);
    }

    // Encontrar sucursal por codigo
    default Sucursal encontrarSucursalPorCodigo(String codigoSucursal) {
        Optional<Sucursal> optionalSucursal = findById(codigoSucursal);
        return optionalSucursal.orElse(null);
    }

    // Encontrar bodega por codigo
    default Bodega encontrarBodegaPorCodigo(String codigoBodega) {
        return findAll().stream()
            .map(Sucursal::getBodegas).flatMap(bodegas -> bodegas.stream()).filter(bodega -> bodega.getCodigo().equals(codigoBodega)).findFirst().orElse(null);
    }

    // Agregar bodega a sucursal
    default void agregarBodegaASucursal(String codigoSucursal, Bodega bodega) {
        
        Sucursal sucursal = encontrarSucursalPorCodigo(codigoSucursal);

        // Verificar si la sucursal existe
        if (sucursal == null) {
            throw new RuntimeException("No existe una sucursal con el id: " + codigoSucursal);
        }

        // Verificar si la bodega ya existe
        if (encontrarBodegaPorCodigo(bodega.getCodigo()) != null) {
            throw new RuntimeException("Ya existe una bodega con el id: " + bodega.getCodigo());
        }
        sucursal.getBodegas().add(bodega);
        
        save(sucursal);
    }

    // Eliminar bodega
    default void eliminarBodega(String codigoBodega){

        // Encontrar la bodega
        Bodega bodega = encontrarBodegaPorCodigo(codigoBodega);

        // Verificar si la bodega existe
        if (bodega == null) {
            throw new RuntimeException("No existe una bodega con el codigo: " + codigoBodega);
        }

        // Encontrar la sucursal a la que pertenece la bodega

        Sucursal sucursal = findAll().stream().filter(s -> s.getBodegas().contains(bodega)).findFirst().orElse(null);
            
        
        // Eliminar la bodega de la sucursal

        sucursal.getBodegas().remove(bodega);

        save(sucursal);

    }


    

}


