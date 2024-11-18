package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.modelo.OrdenDeCompra;

public interface OrdenDeCompraRepository extends MongoRepository<OrdenDeCompra, String> {

    // Crear una orden de compra
    default void crearOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        save(ordenDeCompra);
    }

    // Encontrar orden de compra por id
    @Query("{ 'codigo' : ?0 }")
    OrdenDeCompra encontrarOrdenDeCompraPorId(String id);
    }
