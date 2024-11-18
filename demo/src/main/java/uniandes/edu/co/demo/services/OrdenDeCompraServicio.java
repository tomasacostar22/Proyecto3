package uniandes.edu.co.demo.services;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniandes.edu.co.demo.dto.OrdenDeCompraRequest;
import uniandes.edu.co.demo.modelo.DetalleOrden;
import uniandes.edu.co.demo.modelo.EstadoOrden;
import uniandes.edu.co.demo.modelo.OrdenDeCompra;
import uniandes.edu.co.demo.repository.OrdenDeCompraRepository;
import uniandes.edu.co.demo.repository.ProductoRepository;
import uniandes.edu.co.demo.repository.ProveedorRepository;
import uniandes.edu.co.demo.repository.SucursalRepository;


@Service
public class OrdenDeCompraServicio {

    
    @Autowired
    OrdenDeCompraRepository ordenDeCompraRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    public void crearOrdenDeCompra(OrdenDeCompraRequest ordenDeCompraRequest) {
        
        // Crear la orden de compra
        OrdenDeCompra ordenDeCompra = new OrdenDeCompra();

        // Verificar id
        if (ordenDeCompraRepository.encontrarOrdenDeCompraPorId(ordenDeCompraRequest.getCodigo()) != null) {
            throw new RuntimeException("Ya existe una orden de compra con el id: " + ordenDeCompraRequest.getCodigo());
        }
        ordenDeCompra.setCodigo(ordenDeCompraRequest.getCodigo());
        
        // Datos del request del usuario
        ordenDeCompra.setFechaEntrega(ordenDeCompraRequest.getFechaEntrega());

        // Verificar que el proveedor exista
        if (proveedorRepository.encontrarProveedorPorNit(ordenDeCompraRequest.getNitProveedor()) == null) {
            throw new RuntimeException("No existe un proveedor con el nit: " + ordenDeCompraRequest.getNitProveedor());
        }
        ordenDeCompra.setNitProveedor(ordenDeCompraRequest.getNitProveedor());

        // Verificar que la sucursal exista
        if (sucursalRepository.encontrarSucursalPorCodigo(ordenDeCompraRequest.getCodigoSucursal()) == null) {
            throw new RuntimeException("No existe una sucursal con el codigo: " + ordenDeCompraRequest.getCodigoSucursal());
        }
        ordenDeCompra.setCodigoSucursal(ordenDeCompraRequest.getCodigoSucursal());

        // Verfiicar al menos un producto
        if (ordenDeCompraRequest.getDetalleOrden().isEmpty()) {
            throw new RuntimeException("La orden de compra debe tener al menos un producto");
        }

        // Verificar que los productos existan
        for (DetalleOrden detalleOrden : ordenDeCompraRequest.getDetalleOrden()) {
            if (productoRepository.encontrarProductoPorCodigo(detalleOrden.getCodigo_barras_producto()) == null) {
                throw new RuntimeException("No existe un producto con el codigo de barras: " + detalleOrden.getCodigo_barras_producto());
            }
        }

        // Guardar productos
        ordenDeCompra.setDetalleOrden(ordenDeCompraRequest.getDetalleOrden());

        // Datos de negocio
        ordenDeCompra.setEstado(EstadoOrden.PENDIENTE);
        
        // guardar fecha actual
        ordenDeCompra.setFechaCreacion(new Date());


        // Guardar la orden de compra
        ordenDeCompraRepository.crearOrdenDeCompra(ordenDeCompra);
    }
}
