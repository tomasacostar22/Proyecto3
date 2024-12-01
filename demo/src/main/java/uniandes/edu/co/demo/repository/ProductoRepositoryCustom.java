package uniandes.edu.co.demo.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public ProductoRepositoryCustom(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Filtra productos según rango de precio, fecha de expiración,
     * sucursal y categoría.
     *
     * @return Lista de productos filtrados.
     */
    public List<Document> filtrarProductos(int menor,int mayor,String fecha, String sucursal,String categoria) {
        List<Document> pipeline = List.of(
            // Primer lookup: Vincula con la colección Inventario
            new Document("$lookup", new Document()
                .append("from", "Inventario")
                .append("localField", "_id")
                .append("foreignField", "codigoBarras")
                .append("as", "Inventario")
            ),
            // Segundo lookup: Vincula con la colección Sucursales
            new Document("$lookup", new Document()
                .append("from", "Sucursal")
                .append("localField", "Inventario.codigoBodega")
                .append("foreignField", "bodegas.codigo")
                .append("as", "Sucursales")
            ),
            // Tercer lookup: Vincula con la colección Categorias
            new Document("$lookup", new Document()
                .append("from", "Categorias")
                .append("localField", "codigoCategoria")
                .append("foreignField", "_id")
                .append("as", "Categoria")
            ),
            // Filtro con $match
            new Document("$match", new Document()
                .append("$and", List.of(
                    // Rango de precio
                    new Document("precio", new Document()
                        .append("$gte", menor)
                        .append("$lte", mayor)
                    ),
                    // Fecha de expiración
                    new Document("fechaExpiracion", new Document()
                        .append("$gt", fecha) // Formato ISODate
                    ),
                    // Sucursal específica
                    new Document("$or", List.of(
                        new Document("Sucursales.nombre", sucursal), // Sucursal específica
                        new Document("Categoria.nombre", categoria) // Categoría específica
                    ))
                ))
            )
        );

        // Ejecutar el pipeline en la colección "Productos"
        return mongoTemplate.getCollection("Productos").aggregate(pipeline).into(new java.util.ArrayList<>());
    }
}
