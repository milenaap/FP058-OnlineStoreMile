# 🛍️ Javinity - OnlineStore

**Javinity** es una aplicación de consola desarrollada en Java que permite gestionar artículos, clientes (estándar y premium) y pedidos. Está estructurada siguiendo el patrón de diseño **MVC**, con persistencia de datos implementada mediante **JPA** (Hibernate) y **MySQL**.

---

## 📌 Funcionalidades

- Añadir y mostrar artículos.
- Registrar clientes estándar o premium con herencia JPA.
- Crear y eliminar pedidos (solo si no han sido enviados).
- Filtrar pedidos pendientes y enviados por cliente.
- Registro automático de cliente si no existe al crear pedido.
- Lógica de negocio validada desde los controladores.
- Mensajes claros y validaciones por consola.
- Persistencia sin escribir SQL manual gracias a Hibernate.

---

## 📁 Estructura del Proyecto

- `modelos/` → Entidades JPA: `Cliente`, `Articulo`, `Pedido`, etc.
- `controladores/` → Lógica de negocio para cada entidad.
- `vistas/` → Menús de interacción por consola.
- `excepciones/` → Excepciones personalizadas como `ElementoNoEncontradoException`.
- `dao/implementaciones/` → DAOs implementados usando `EntityManager` (JPA).
- `Main.java` → Clase principal que arranca el menú de la aplicación.

---

## ▶️ Cómo ejecutar

1. Asegúrate de tener MySQL activo con una base de datos `online_store_db`.
2. Configura el `persistence.xml` (si aplica) o asegúrate de que `hibernate.cfg.xml` tenga los datos correctos.
3. Abre el proyecto en IntelliJ u otro IDE.
4. Ejecuta la clase `Main.java`.
5. Usa el menú para navegar entre artículos, clientes y pedidos.

---

## 🎯 Tecnologías

- ✅ Java 23 (OpenJDK)
- ✅ Hibernate ORM (JPA)
- ✅ MySQL
- ✅ Jakarta Persistence API
- ✅ JUnit 5 (para pruebas unitarias)
- ✅ Patrón MVC

---

## 🛠️ Diseño e implementación

- 🧩 **JPA + Hibernate**: Los modelos son entidades JPA con anotaciones `@Entity`, `@Id`, `@ManyToOne`, etc.
- 🧬 **Herencia JPA**: `Cliente` es abstracta; `ClientePremium` y `ClienteEstandar` heredan y se persisten en tablas separadas.
- 🔄 **Eliminación controlada**: Los pedidos solo pueden eliminarse si no han sido enviados, validado con `LocalDateTime`.
- 🧪 **JUnit 5**: Se crearon tests para comprobar la correcta persistencia y lógica de negocio.

---

## ✅ Pruebas realizadas

- ✔️ Agregar artículos y clientes desde consola → Confirmado con consultas en MySQL.
- ✔️ Registro automático de cliente al crear pedido → Valida herencia y persistencia.
- ✔️ Eliminación restringida de pedidos enviados → Dispara correctamente `PedidoNoEliminableException`.
- ✔️ Uso de `EntityManager` en DAOs → Sin SQL manual, todo mediante JPA.

---

## 👨‍🎓 Proyecto académico

Este proyecto ha sido desarrollado como entrega final para la asignatura **Programación Orientada a Objetos con Base de Datos**, dentro del ciclo formativo de **Desarrollo de Aplicaciones Web**. La evolución del código ha permitido incorporar buenas prácticas y migrar de JDBC a JPA.

---

## 🌐 Repositorio

🔗 GitHub: [https://github.com/milenaap/FP058-OnlineStoreMile](https://github.com/milenaap/FP058-OnlineStoreMile)
