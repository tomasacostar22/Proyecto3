package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.modelo.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {


    // Encontrar producto por codigo
    @Query("{ 'codigoBarras' : ?0 }")
    Producto encontrarProductoPorCodigo(String codigoProducto);

    // Encontrar producto por nombre
    @Query("{ 'nombre' : ?0 }")
    Producto encontrarProductoPorNombre(String nombreProducto);


    }

