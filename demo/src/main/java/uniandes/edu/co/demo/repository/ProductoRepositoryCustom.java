package uniandes.edu.co.demo.repository;

import java.sql.Date;
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
    public List<Document> filtrarProductosMayor(int menor, int mayor, String fecha, String sucursal, String categoria) {
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
                    // Fecha de expiración (convertida a ISODate)
                    new Document("fechaExpiracion", new Document()
                        .append("$gt", ISODate(fecha)) // Convierte la fecha a ISODate
                    ),
                    // Sucursal y categoría específicas
                    new Document("$or", List.of(
                        new Document("Sucursales._id", sucursal), // Compara con _id en lugar de nombre
                        new Document("Categoria.nombre", categoria) // Compara el nombre de la categoría
                    ))
                ))
            )
        );

        // Log para depurar el pipeline
        System.out.println("Pipeline construido: " + pipeline);

        // Ejecutar el pipeline en la colección "Productos"
        return mongoTemplate.getCollection("Productos").aggregate(pipeline).into(new java.util.ArrayList<>());
    }

    public List<Document> filtrarProductosMenor(int menor, int mayor, String fecha, String sucursal, String categoria) {
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
                    // Fecha de expiración menor a la proporcionada
                    new Document("fechaExpiracion", new Document()
                        .append("$lt", ISODate(fecha)) // Convierte la fecha a ISODate
                    ),
                    // Sucursal y categoría específicas
                    new Document("$or", List.of(
                        new Document("Sucursales._id", sucursal), // Compara con _id en lugar de nombre
                        new Document("Categoria.nombre", categoria) // Compara el nombre de la categoría
                    ))
                ))
            )
        );

        // Log para depurar el pipeline
        System.out.println("Pipeline construido: " + pipeline);

        // Ejecutar el pipeline en la colección "Productos"
        return mongoTemplate.getCollection("Productos").aggregate(pipeline).into(new java.util.ArrayList<>());
    }

    // Helper para convertir la fecha en ISODate
    private Object ISODate(String fecha) {
        try {
            return new Date(java.sql.Date.valueOf(fecha).getTime());
        } catch (Exception e) {
            throw new RuntimeException("Formato de fecha inválido. Use YYYY-MM-DD.", e);
        }
    }
}
