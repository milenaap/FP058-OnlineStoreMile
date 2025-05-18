
# 🛒 OnlineStore - Javinity (JavaFX + JPA + Hibernate)

Este proyecto es una aplicación de escritorio desarrollada en Java utilizando **JavaFX** como interfaz gráfica. Permite registrar y administrar **clientes**, **artículos** y **pedidos**, con persistencia en base de datos utilizando **JPA (Jakarta Persistence API)** e implementación con **Hibernate ORM**.

---

## ✅ Características implementadas

- Interfaz gráfica moderna con **JavaFX**
- Persistencia de datos en base de datos **MySQL** mediante **JPA + Hibernate**
- CRUD completo para:
    - **Artículos**
    - **Clientes**
    - **Pedidos**
- Gestión de pedidos:
    - Añadir pedido
    - Eliminar pedido (con validación de estado)
    - Mostrar todos los pedidos
    - Filtrar por pedidos pendientes o enviados por cliente
- Manejo de excepciones personalizadas:
    - `ElementoNoEncontradoException`
    - `PedidoNoEliminableException`
- Navegación entre vistas desde un **menú principal con logo**
- Estilo visual uniforme mediante archivo `styles.css`
- Icono personalizado para la aplicación

---

## 🧱 Tecnologías utilizadas

| Herramienta         | Versión    |
|---------------------|------------|
| Java                | 23         |
| JavaFX              | 21         |
| Hibernate ORM       | 6.4.4.Final|
| Jakarta Persistence | 3.1.0      |
| MySQL Connector/J   | 8.0.33     |
| IntelliJ IDEA       | (recomendado) |

---

## 🖼️ Interfaz de usuario

- Se ha creado una interfaz limpia y organizada utilizando `BorderPane`, `VBox` y `TableView`.
- Las vistas disponibles son:
    - `Menú Principal`
    - `Gestión de Artículos`
    - `Gestión de Clientes`
    - `Gestión de Pedidos`
- Cada módulo cuenta con un sidebar y formulario adaptado con botones funcionales.

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
            ├── modelos/           --> Entidades JPA
            └── vistas/            --> Interfaces JavaFX (Menu, Articulos, Clientes, Pedidos)
    └── resources/
        ├── META-INF/persistence.xml  --> Configuración de JPA y Hibernate
        ├── styles.css                --> Estilos visuales para JavaFX
        └── images/logo.png          --> Logo de la tienda para el menú
```

---

## 🔧 Configuración de base de datos

- Nombre: `online_store_db`
- Motor: MySQL
- Creación automática de tablas activada (`hibernate.hbm2ddl.auto = update`)

Tablas utilizadas:
- `clientes`
- `articulos`
- `pedidos`

---

## ▶️ Cómo ejecutar el proyecto

1. Clona el repositorio
2. Asegúrate de tener MySQL activo y con la base de datos `online_store_db` creada
3. Configura tu contraseña en `persistence.xml`
4. Ejecuta `MenuPrincipal.java` para abrir la interfaz
5. Interactúa con el sistema mediante la GUI JavaFX

---

## 📌 Notas importantes

- Este proyecto es una evolución del producto 4 que funcionaba por consola. Ahora se utiliza interfaz gráfica con JavaFX para cumplir los requisitos del **Producto 5**.
- Se ha mantenido el patrón MVC: solo se ha sustituido la capa de vista.
- Las operaciones de guardado y eliminación requieren validaciones de entrada.

---

👩‍💻 **Desarrollado por el grupo Javinity – UOC FPDAW**
