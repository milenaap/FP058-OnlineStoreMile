/**
 Vista JavaFX encargada de la gestión de artículos.
 Permite agregar, mostrar y eliminar artículos desde una interfaz gráfica.
 Utiliza el controlador ArticuloControlador para gestionar la lógica de negocio.
 Forma parte de la capa de Vista del patrón MVC.
 */
package org.javinity.vistas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.javinity.controladores.ArticuloControlador;
import org.javinity.modelos.Articulo;

public class ArticuloVista {
    private final ArticuloControlador articuloControlador;

    /**
     * Constructor que recibe el controlador asociado a la vista.
     * @param articuloControlador controlador de artículos
     */
    public ArticuloVista(ArticuloControlador articuloControlador) {
        this.articuloControlador = articuloControlador;
    }

    /**
     * Metodo principal que muestra la ventana de gestión de artículos.
     */
    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Artículos");

        VBox sidebar = new VBox(10);
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2e2e2e;");
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label sidebarTitle = new Label("GESTIÓN DE ARTÍCULOS");
        sidebarTitle.setStyle("-fx-text-fill: #888; -fx-font-size: 14px; -fx-font-weight: bold;");

        Button btnNuevo = new Button("Añadir");
        Button btnMostrar = new Button("Mostrar");
        Button btnVolver = new Button("Menú principal");

        Button btnGuardar = new Button("Guardar");
        Button btnEliminar = new Button("Eliminar");

        for (Button btn : new Button[]{btnNuevo, btnMostrar}) {
            btn.getStyleClass().add("sidebar-button");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);
        }

        btnVolver.getStyleClass().add("back-button");
        btnVolver.setMaxWidth(Double.MAX_VALUE);
        btnEliminar.getStyleClass().add("danger-button");
        btnGuardar.getStyleClass().add("button");

        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(20));

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código del producto");

        TextField txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción");

        TextField txtPrecioVenta = new TextField();
        txtPrecioVenta.setPromptText("Precio de venta (€)");

        TextField txtGastosEnvio = new TextField();
        txtGastosEnvio.setPromptText("Gastos de envío (€)");

        TextField txtTiempo = new TextField();
        txtTiempo.setPromptText("Tiempo de preparación (min)");

        formulario.getChildren().addAll(txtCodigo, txtDescripcion, txtPrecioVenta, txtGastosEnvio, txtTiempo);

        HBox formularioBotones = new HBox(10);
        formularioBotones.setPadding(new Insets(0, 20, 10, 20));
        formularioBotones.setAlignment(Pos.CENTER_RIGHT);
        formularioBotones.getChildren().addAll(btnGuardar, btnEliminar);

        TableView<Articulo> tabla = new TableView<>();
        tabla.setPlaceholder(new Label("No hay artículos registrados."));

        btnNuevo.setOnAction(e -> {
            txtCodigo.clear();
            txtDescripcion.clear();
            txtPrecioVenta.clear();
            txtGastosEnvio.clear();
            txtTiempo.clear();
            txtCodigo.requestFocus();
        });

        btnGuardar.setOnAction(e -> {
            try {
                String codigo = txtCodigo.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                String precioStr = txtPrecioVenta.getText().trim();
                String envioStr = txtGastosEnvio.getText().trim();
                String tiempoStr = txtTiempo.getText().trim();

                if (codigo.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty() || envioStr.isEmpty() || tiempoStr.isEmpty()) {
                    throw new IllegalArgumentException("Por favor, introduce todos los datos para el nuevo artículo.");
                }

                float precio = Float.parseFloat(precioStr);
                float envio = Float.parseFloat(envioStr);
                int tiempo = Integer.parseInt(tiempoStr);

                Articulo nuevo = new Articulo(codigo, descripcion, precio, envio, tiempo);
                articuloControlador.agregarArticulo(nuevo);
                new Alert(Alert.AlertType.INFORMATION, "Artículo guardado correctamente.").showAndWait();

                txtCodigo.clear(); txtDescripcion.clear(); txtPrecioVenta.clear(); txtGastosEnvio.clear(); txtTiempo.clear();

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).showAndWait();
            }
        });

        btnMostrar.setOnAction(e -> {
            ObservableList<Articulo> articulos = FXCollections.observableArrayList(articuloControlador.obtenerTodos());

            TableColumn<Articulo, String> colCodigo = new TableColumn<>("Código");
            colCodigo.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getCodigoProducto()));

            TableColumn<Articulo, String> colDesc = new TableColumn<>("Descripción");
            colDesc.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getDescripcion()));

            TableColumn<Articulo, String> colPrecio = new TableColumn<>("Precio (€)");
            colPrecio.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getPrecioVenta())));

            TableColumn<Articulo, String> colEnvio = new TableColumn<>("Envío (€)");
            colEnvio.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getGastosEnvio())));

            TableColumn<Articulo, String> colTiempo = new TableColumn<>("Preparación (min)");
            colTiempo.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getTiempoPrepEnvio())));

            tabla.getColumns().setAll(colCodigo, colDesc, colPrecio, colEnvio, colTiempo);
            tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tabla.setItems(articulos);
        });

        btnEliminar.setOnAction(e -> {
            Articulo seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                new Alert(Alert.AlertType.WARNING, "Debes seleccionar un artículo para eliminarlo.").showAndWait();
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar el artículo seleccionado?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    articuloControlador.eliminarArticulo(seleccionado);
                    tabla.getItems().remove(seleccionado);
                }
            });
        });

        btnVolver.setOnAction(e -> stage.close());

        sidebar.getChildren().addAll(sidebarTitle, btnNuevo, btnMostrar, btnVolver);

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.getChildren().addAll(formulario, formularioBotones, tabla);

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(content);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
