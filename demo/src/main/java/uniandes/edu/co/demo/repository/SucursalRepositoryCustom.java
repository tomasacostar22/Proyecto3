package uniandes.edu.co.demo.repository;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SucursalRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public SucursalRepositoryCustom(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Genera un reporte para una sucursal específica, mostrando las bodegas,
     * productos, cantidades disponibles, cantidades mínimas y costo promedio.
     *
     * @param nombreSucursal Nombre de la sucursal para la cual se quiere el reporte.
     * @return Lista de bodegas con los productos asociados y sus detalles.
     */
    public List<Document> generarReportePorSucursal(String nombreSucursal) {
        List<Document> pipeline = List.of(
            // Filtrar por la sucursal específica
            new Document("$match", new Document("_id", nombreSucursal)),
            
            // Descomponer el array de bodegas
            new Document("$unwind", "$bodegas"),
            
            // Vincular las bodegas con la colección Inventario
            new Document("$lookup", new Document()
                .append("from", "Inventario")
                .append("localField", "bodegas.codigo")
                .append("foreignField", "codigoBodega")
                .append("as", "inventario")
            ),
            
            // Descomponer el array de inventario
            new Document("$unwind", "$inventario"),
            
            // Vincular los productos del inventario con la colección Productos
            new Document("$lookup", new Document()
                .append("from", "Productos")
                .append("localField", "inventario.codigoBarras")
                .append("foreignField", "_id")
                .append("as", "producto")
            ),
            
            // Descomponer el array de productos
            new Document("$unwind", "$producto"),
            
            // Proyectar los campos relevantes
            new Document("$project", new Document()
                .append("_id", 0)
                .append("bodega", "$bodegas.nombre")
                .append("producto", "$producto.nombre")
                .append("cantidadDisponible", "$inventario.cantidad")
                .append("cantidadMinima", "$inventario.nivelMinimo")
                .append("costoPromedio", "$inventario.costoBodega")
            ),
            
            // Agrupar por bodega
            new Document("$group", new Document()
                .append("_id", "$bodega")
                .append("productos", new Document()
                    .append("$push", new Document()
                        .append("nombre", "$producto")
                        .append("cantidadDisponible", "$cantidadDisponible")
                        .append("cantidadMinima", "$cantidadMinima")
                        .append("costoPromedio", "$costoPromedio")
                    )
                )
            )
        );

        // Ejecutar el pipeline en la colección "Sucursales"
        return mongoTemplate.getCollection("Sucursal").aggregate(pipeline).into(new java.util.ArrayList<>());
    }
}
