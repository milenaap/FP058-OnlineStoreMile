
# 🛒 OnlineStore - Javinity (POO + JPA + Hibernate)

Este proyecto es una aplicación de consola desarrollada en Java que gestiona una tienda online. Permite registrar y administrar clientes, artículos y pedidos, utilizando persistencia en base de datos con **JPA** e implementación con **Hibernate ORM**.

---

## ✅ Características implementadas

- Persistencia de datos en base de datos **MySQL** mediante **JPA (Jakarta Persistence API)** y **Hibernate ORM**
- Uso del **patrón DAO** desacoplado para la lógica de acceso a datos
- CRUD completo para **Artículos** y **Clientes**
- Gestión de **Pedidos**:
  - Añadir pedido (con registro automático de cliente si no existe)
  - Eliminar pedido (solo si aún no ha sido enviado)
  - Listado general de pedidos
  - Filtrado de pedidos **pendientes** y **enviados** por cliente
- Manejo de excepciones personalizadas:
  - `ElementoNoEncontradoException`
  - `PedidoNoEliminableException`
- Estructura basada en el patrón **MVC** con paquetes organizados:
  - modelos, controladores, vistas, dao, excepciones

---

## 🧱 Tecnologías utilizadas

| Herramienta         | Versión    |
|---------------------|------------|
| Java                | 23         |
| Hibernate ORM       | 6.4.4.Final|
| Jakarta Persistence | 3.1.0      |
| MySQL Connector/J   | 8.0.33     |
| IntelliJ IDEA       | (recomendado) |

---

## 🗂 Estructura del proyecto

```
src/
└── main/
    └── java/
        └── org/javinity/
            ├── controladores/     --> Lógica de negocio
            ├── dao/               --> DAOs implementados con JPA
            ├── excepciones/       --> Excepciones personalizadas
            ├── modelos/           --> Clases JPA: Cliente, Artículo, Pedido, etc.
            └── vistas/            --> Consola: menús y entrada del usuario
    └── resources/
        └── META-INF/
            └── persistence.xml    --> Configuración de JPA y Hibernate
```

---

## 🔧 Configuración de base de datos

- Nombre de la base de datos: `onlinestore`
- Motor: MySQL

### Tablas esperadas:

- `clientes` (email PK)
- `articulos` (codigo_producto PK)
- `pedidos` (num_pedido PK, FK email_cliente, FK codigo_producto)

> Las tablas se crean automáticamente gracias a:
```xml
<property name="hibernate.hbm2ddl.auto" value="update"/>
```

---

## 🧪 `persistence.xml` (resumen)

```xml
<persistence-unit name="OnlineStorePU" transaction-type="RESOURCE_LOCAL">
    ...
    <properties>
        <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/online_store_db"/>
        <property name="jakarta.persistence.jdbc.user" value="root"/>
        <property name="jakarta.persistence.jdbc.password" value="tu_contraseña"/>
        <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
        
        <!-- Dialecto recomendado -->
        <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>

        <property name="hibernate.show_sql" value="true"/>
        <property name="hibernate.format_sql" value="true"/>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
    </properties>
</persistence-unit>
```

---

## ▶️ Cómo ejecutar el proyecto

1. Clona el repositorio.
2. Asegúrate de tener MySQL activo y con la base de datos `online_store_db` creada.
3. Configura tu contraseña en `persistence.xml`.
4. Ejecuta `Main.java` desde tu IDE favorito.
5. ¡Explora el menú por consola!

---

## 📌 Notas

- Se ha eliminado el uso de interfaces y factory para simplificar el proyecto.
- Hibernate genera y actualiza las tablas automáticamente si no existen.
- El código es limpio, comentado y diseñado para facilitar la comprensión de **POO + JPA**.

---

👩‍💻 **Desarrollado por el grupo Javinity – UOC FPDAW**
