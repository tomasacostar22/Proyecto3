package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.modelo.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {

    // Crear una categoria
    default void crearCategoria(Categoria categoria) {
        // Verificar si ya existe la categoria
        if (encontrarCategoriaPorCodigo(categoria.getCodigo()) != null) {
            throw new RuntimeException("Ya existe una categoria con el id: " + categoria.getCodigo());
        }
        save(categoria);
    }
    
    // Encontrar categoria por codigo
    @Query("{ 'codigo' : ?0 }")
    Categoria encontrarCategoriaPorCodigo(String codigoCategoria);
}