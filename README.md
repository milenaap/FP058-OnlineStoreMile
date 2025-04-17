# 🛍️ Javinity - OnlineStore

**Javinity** es una aplicación de consola desarrollada en Java que permite gestionar artículos, clientes y pedidos. Está estructurada siguiendo el patrón de diseño **MVC**, separando claramente la lógica de negocio, la interfaz de usuario y los modelos de datos.

---

## 📌 Funcionalidades

- Añadir y mostrar artículos.
- Registrar clientes estándar o premium.
- Crear y eliminar pedidos (con validación del tiempo de preparación).
- Visualizar pedidos pendientes o enviados filtrados por cliente.
- Registro automático del cliente si no existe al crear un pedido.
- Validación de datos y mensajes de error claros.

---

## 📁 Estructura del Proyecto

- `modelos`: contiene las clases del dominio (`Cliente`, `Articulo`, `Pedido`).
- `controladores`: gestiona la lógica de negocio (una por cada entidad).
- `vistas`: gestiona la interacción por consola (menús y formularios).
- `excepciones`: clases personalizadas para errores comunes.
- `dao`: acceso a datos mediante interfaces e implementaciones JDBC.
- `dao.factory`: clase `DAOFactory` para desacoplar la persistencia.
- `DatabaseConnection`: utilidad para controlar las conexiones.

---

## ▶️ Cómo ejecutar

1. Asegúrate de tener MySQL funcionando con la base de datos configurada.
2. Abre el proyecto en tu IDE (IntelliJ, Eclipse, etc.).
3. Ejecuta la clase `Main.java`.
4. Usa el menú principal para navegar entre módulos.

---

## 🎯 Tecnologías

- Java 17+
- JDBC (con transacciones)
- MySQL
- Patrón MVC + DAO + Factory
- `LocalDateTime`, `Scanner`, excepciones personalizadas

---

## 🛠️ Diseño e implementación

- 🔁 **Patrón DAO**: se aplica para separar la lógica de acceso a datos, facilitando el mantenimiento y la escalabilidad.
- 🧪 **Patrón Factory**: utilizado para instanciar los diferentes DAO (`ArticuloDAO`, `ClienteDAO`, `PedidoDAO`) a través de la clase `DAOFactory`, logrando independencia entre la lógica de negocio y el motor de persistencia.
- 💾 **Transacción JDBC**: implementada en la clase `PedidoDAOImpl` para asegurar la atomicidad de la operación `insertar(Pedido)`, garantizando que el pedido se almacene correctamente o no se almacene nada en caso de error.
- ⚙️ **Procedimiento almacenado**: se ha implementado el procedimiento `obtener_pedidos_por_cliente` en MySQL, invocado mediante JDBC, que retorna todos los pedidos asociados a un cliente dado su correo electrónico.

---

## 👩‍💻 Proyecto académico

Este proyecto forma parte del módulo de **Programación Orientada a Objetos** del ciclo de **Desarrollo de Aplicaciones Web**. La aplicación evoluciona en cada entrega, incorporando buenas prácticas y patrones de diseño, con el objetivo de acercarse a una arquitectura de software profesional.

---

## 🌐 Repositorio

🔗 GitHub: [https://github.com/milenaap/FP058-OnlineStoreMile](https://github.com/milenaap/FP058-OnlineStoreMile)
