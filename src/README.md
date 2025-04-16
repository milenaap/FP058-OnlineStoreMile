# OnlineStore - Grupo Nº10 - Javinity - Proyecto POO con Persistencia en MySQL

Este proyecto es una aplicación de consola desarrollada en Java que gestiona una tienda online. Incluye funcionalidades para registrar y administrar clientes, artículos y pedidos.

## ✅ Características implementadas

- Persistencia de datos en base de datos MySQL mediante JDBC
- Uso del patrón DAO para separar la lógica de acceso a datos
- Patrón Factory para instanciación desacoplada de DAOs
- CRUD completo para Artículos y Clientes
- Gestión de Pedidos:
    - Añadir pedido (con registro automático de cliente si no existe)
    - Eliminar pedido (solo si no ha sido enviado)
    - Listado de pedidos
    - Filtrado de pedidos pendientes y enviados por cliente
- Procedimiento almacenado `sp_insertar_pedido` en MySQL
- Manejo de excepciones específicas: `ElementoNoEncontradoException`, `PedidoNoEliminableException`
- Estructura organizada en paquetes: modelos, controladores, vistas, dao

## 🛠 Requisitos

- Java 17 o superior
- MySQL Server
- Driver JDBC para MySQL
- IDE recomendado: IntelliJ

## 🗂 Estructura del proyecto

- `modelos/` → Clases de dominio (`Cliente`, `Articulo`, `Pedido`, etc.)
- `dao/interfaces/` → Interfaces DAO
- `dao/implementaciones/` → Clases DAO con JDBC
- `dao/factory/` → `DAOFactory` para inyectar dependencias
- `controladores/` → Lógica de negocio
- `vistas/` → Interfaz por consola (menús)
- `DatabaseConnection.java` → Clase para manejar la conexión JDBC
- `Main.java` → Punto de entrada del programa

## 📦 Base de datos

Nombre de la base de datos: `onlinestore`

Tablas:
- `clientes` (email PK)
- `articulos` (codigo_producto PK)
- `pedidos` (num_pedido PK, FK email_cliente, FK codigo_producto)

Procedimiento almacenado:
```sql
DELIMITER $$

CREATE PROCEDURE sp_insertar_pedido (
  IN p_email_cliente VARCHAR(100),
  IN p_codigo_producto VARCHAR(50),
  IN p_cantidad INT,
  IN p_fecha_hora DATETIME
)
BEGIN
  INSERT INTO pedidos (email_cliente, codigo_producto, cantidad, fecha_hora)
  VALUES (p_email_cliente, p_codigo_producto, p_cantidad, p_fecha_hora);
END $$

DELIMITER ;


