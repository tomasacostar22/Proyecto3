package uniandes.edu.co.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniandes.edu.co.demo.modelo.Producto;
import uniandes.edu.co.demo.repository.CategoriaRepository;
import uniandes.edu.co.demo.repository.ProductoRepository;

@Service
public class ProductoServicio {
    

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void crearProducto(Producto producto) {

        if (productoRepository.encontrarProductoPorCodigo(producto.getCodigoBarras()) != null) {
            throw new RuntimeException("Ya existe un producto con el codigo de barras: " + producto.getCodigoBarras());
        }

        if (productoRepository.encontrarProductoPorNombre(producto.getNombre()) != null) {
            throw new RuntimeException("Ya existe un producto con el nombre: " + producto.getNombre());
        }

        if (categoriaRepository.encontrarCategoriaPorCodigo(producto.getCodigoCategoria()) == null) {
            throw new RuntimeException("No existe una categoria con el codigo: " + producto.getCodigoCategoria());
        }

        productoRepository.save(producto);
    }

    public void actualizarProducto(String codigoBarrasProductoViejo, Producto productoNuevo){

        // Validar que el producto viejo exista
        Producto productoViejo = productoRepository.encontrarProductoPorCodigo(codigoBarrasProductoViejo);
        if (productoViejo == null) {
            throw new RuntimeException("No existe un producto con el codigo de barras: " + codigoBarrasProductoViejo);
        }

        // Validar que la categoria exista
        if (categoriaRepository.encontrarCategoriaPorCodigo(productoNuevo.getCodigoCategoria()) == null) {
            throw new RuntimeException("No existe una categoria con el codigo: " + productoNuevo.getCodigoCategoria());
        }

        // Validar que el producto viejo y el producto nuevo tengan el mismo codigo de barras
        if (!codigoBarrasProductoViejo.equals(productoNuevo.getCodigoBarras())) {
            throw new RuntimeException("El codigo de barras del producto a actualizar y el producto nuevo no coinciden");
        }

        productoViejo.setNombre(productoNuevo.getNombre());
        productoViejo.setPresentacion(productoNuevo.getPresentacion());
        productoViejo.setCantidad(productoNuevo.getCantidad());
        productoViejo.setUnidadMedida(productoNuevo.getUnidadMedida());
        productoViejo.setCodigoCategoria(productoNuevo.getCodigoCategoria());
        productoViejo.setFechaExpiracion(productoNuevo.getFechaExpiracion());
        productoViejo.setPrecio(productoNuevo.getPrecio());

        productoRepository.save(productoViejo);
    }
        
}
