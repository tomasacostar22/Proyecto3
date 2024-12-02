
# Documentación del Proyecto

## Estructura General

El proyecto implementa una solución basada en MongoDB que gestiona la relación entre sucursales, inventarios, productos, órdenes de compra, proveedores y categorías. 
Se diseñó usando documentos embebidos y referencias según las necesidades de cada entidad.

## Modelo de Datos

### 1. Sucursal

**Atributos:**
- **codigo** (string): Código único de la sucursal.
- **nombre** (string): Nombre de la sucursal.
- **tamaño** (number): Tamaño físico de la sucursal.
- **ciudad** (string): Ciudad donde está ubicada.

**Relaciones:**
- Una sucursal tiene múltiples bodegas asociadas.
- Nota: Los datos de las bodegas están referenciados, no embebidos.

### 2. Bodega

**Atributos:**
- **codigo** (string): Código único de la bodega.
- **nombre** (string): Nombre de la bodega.
- **tamaño** (number): Capacidad física de la bodega.

**Relaciones:**
- Cada bodega está asociada a una sucursal mediante un campo de referencia.
- Está relacionada con el inventario.

### 3. Inventario

**Atributos:**
- **codigo_barras** (string): Código único del producto.
- **cantidad** (number): Cantidad disponible en la bodega.
- **nivel_minimo** (number): Cantidad mínima permitida en stock.
- **costo_bodega** (number): Costo asociado al producto dentro de la bodega.
- **codigoBodega** (string): Referencia a la bodega donde se almacena el producto.

**Relaciones:**
- Relacionado con un producto y una bodega.

### 4. Producto

**Atributos:**
- **codigo_barras** (string): Código único del producto.
- **nombre** (string): Nombre del producto.
- **presentacion** (string): Presentación (por ejemplo, botella, caja).
- **cantidad** (number): Cantidad por presentación.
- **unidad_medida** (string): Unidad de medida del producto (litros, kg, etc.).
- **codigo_categoria** (string): Referencia a la categoría del producto.
- **fecha_expiracion** (date): Fecha de expiración.
- **precio** (number): Precio del producto.

**Relaciones:**
- Cada producto pertenece a una categoría.

### 5. Categoría

**Atributos:**
- **codigo** (string): Código único de la categoría.
- **nombre** (string): Nombre de la categoría.
- **descripcion** (string): Descripción general.
- **caracteristicas_almacenamiento** (string): Requisitos de almacenamiento específicos.

**Relaciones:**
- Una categoría puede contener varios productos.

### 6. Orden de Compra

**Atributos:**
- **estado** (string): Estado de la orden (pendiente, completada, cancelada).
- **NIT_proveedor** (string): Referencia al proveedor asociado.
- **codigo_sucursal** (string): Referencia a la sucursal.
- **productos** (array): Lista de detalles embebidos.

**Relaciones:**
- Relacionada con un proveedor y una sucursal.
- Contiene detalles embebidos.

### 7. Detalle (Embebido en Orden de Compra)

**Atributos:**
- **codigo_barras** (string): Código del producto.
- **cantidad** (number): Cantidad solicitada.
- **precio** (number): Precio unitario.

**Relaciones:**
- Forma parte de la orden de compra.

### 8. Recepción de Producto

**Atributos:**
- **fecha** (date): Fecha de recepción.
- **hora** (time): Hora de recepción.
- **codigo_Bodega** (string): Referencia a la bodega receptora.

**Relaciones:**
- Relacionada con bodegas e inventarios.

### 9. Proveedor

**Atributos:**
- **NIT** (string): Identificación única del proveedor.
- **nombre** (string): Nombre del proveedor.
- **direccion** (string): Dirección del proveedor.
- **nombre_contacto** (string): Nombre del contacto principal.
- **telefono** (string): Teléfono de contacto.

**Relaciones:**
- Relacionado con órdenes de compra.

## Relaciones

**1 a N:**
- Sucursal → Bodega
- Categoría → Producto
- Orden de Compra → Detalles (embebido)

**N a 1:**
- Producto → Categoría
- Inventario → Producto
- Inventario → Bodega
- Orden de Compra → Proveedor

## Embebidos vs Referencias

| Entidad  | Tipo      | Justificación                                                                 |
|----------|-----------|-------------------------------------------------------------------------------|
| Detalle  | Embebido  | Los detalles son específicos de cada orden de compra.                         |
| Inventario | Referencia | Referencia a productos y bodegas para evitar redundancia.                    |
| Bodega   | Referencia | Relacionada a sucursales sin embebido porque tienen datos propios.            |
| Producto | Referencia | Relacionado a inventario y categorías.                                        |
| Proveedor | Referencia | Reutilizable entre múltiples órdenes de compra.                               |


####Documentacion por Entidad JSON #####
# **Explicación de las Relaciones**

## **Sucursal - Bodega**
* Una sucursal puede tener varias bodegas asociadas.
* Esta relación es **referenciada** mediante el campo `codigoSucursal`.

---

## **Bodega - Inventario**
* Una bodega contiene productos en su inventario.
* Esta relación también es **referenciada**, y los productos se identifican por su `codigoBarras`.

---

## **Inventario - Producto**
* El inventario referencia productos mediante el campo `codigoBarras`.
* Los productos están definidos en su propia colección.

---

## **Producto - Categoría**
* Cada producto pertenece a una categoría, identificada mediante `codigoCategoria`.
* Esta relación es **referenciada**.

---

## **Producto - Sucursal**
* Los productos tienen una relación **embebida** con las sucursales donde están disponibles.
* Se incluye la cantidad disponible en cada sucursal.

---

## **Orden de Compra - Proveedor**
* Una orden de compra referencia a un proveedor mediante su `NIT`.
* La relación es **referenciada**.

---

## **Recepción de Productos**
* Define los productos recibidos en una bodega específica (`codigoBodega`).
* Incluye información de:
  * Fecha.
  * Hora.
  * Lista de los productos recibidos con sus cantidades.
