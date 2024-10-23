package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import java.util.List;
import uniandes.edu.co.demo.modelo.Bar;
import uniandes.edu.co.demo.modelo.Bebida;

public interface BarRepository extends MongoRepository<Bar, Integer> {

    // Consulta todos los bares, pero excluye la oferta de bebidas para mejorar el rendimiento
    @Query(value = "{}", fields = " { 'oferta_bebidas': 0 }")
    List<Bar> buscarTodosLosBares();

    // Busca un bar por su ID
    @Query("{_id: ?0}")
    List<Bar> buscarPorId(int id);

    // Inserta un nuevo bar usando el método save() heredado de MongoRepository
    default void insertarBar(Bar bar) {
        save(bar);  // save() es proporcionado por MongoRepository
    }

    // Actualiza un bar por su ID, modificando los campos nombre, ciudad, presupuesto, etc.
    @Query("{_id: ?0 }")
    @Update("{ $set: { nombre: ?1, ciudad: ?2, presupuesto: ?3, cant_sedes: ?4, oferta_bebidas: ?5 } }")
    void actualizarBar(int id, String nombre, String ciudad, String presupuesto, int cant_sedes, List<Bebida> oferta_bebidas);

    // Elimina un bar por su ID
    @Query(value = "_id: ?0", delete = true)
    void eliminarBarPorId(int id);

    // Obtiene solo la oferta de bebidas de un bar por su ID
    @Query(value = "{ _id: ?0 }", fields = "{ 'oferta_bebidas': 1 }")
    List<Bebida> obtenerBebidasPorBar(int id);

}
