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
import org.javinity.controladores.ClienteControlador;
import org.javinity.controladores.PedidoControlador;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.excepciones.PedidoNoEliminableException;
import org.javinity.modelos.Articulo;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.Pedido;

import java.time.LocalDateTime;

public class PedidoVista {

    private final PedidoControlador pedidoControlador;
    private final ClienteControlador clienteControlador;
    private final ArticuloControlador articuloControlador;

    public PedidoVista(PedidoControlador pedidoControlador, ClienteControlador clienteControlador, ArticuloControlador articuloControlador) {
        this.pedidoControlador = pedidoControlador;
        this.clienteControlador = clienteControlador;
        this.articuloControlador = articuloControlador;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Pedidos");

        VBox sidebar = new VBox(10);
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2e2e2e;");
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label sidebarTitle = new Label("GESTIÓN DE PEDIDOS");
        sidebarTitle.setStyle("-fx-text-fill: #888; -fx-font-size: 14px; -fx-font-weight: bold;");

        Button btnNuevo = new Button("Añadir");
        Button btnMostrar = new Button("Mostrar todos");
        Button btnPendientes = new Button("Pendientes por cliente");
        Button btnEnviados = new Button("Enviados por cliente");
        Button btnVolver = new Button("Menú Principal");

        Button btnAgregar = new Button("Guardar");
        Button btnEliminar = new Button("Eliminar");

        for (Button btn : new Button[]{btnNuevo, btnMostrar, btnPendientes, btnEnviados}) {
            btn.getStyleClass().add("sidebar-button");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);
        }

        btnVolver.getStyleClass().add("back-button");
        btnVolver.setMaxWidth(Double.MAX_VALUE);
        btnAgregar.getStyleClass().add("button");
        btnEliminar.getStyleClass().add("danger-button");

        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(20));

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email del cliente");

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código del artículo");

        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");

        TextField txtNumPedido = new TextField();
        txtNumPedido.setPromptText("Número del pedido (para eliminar)");

        formulario.getChildren().addAll(txtEmail, txtCodigo, txtCantidad, txtNumPedido);

        HBox formularioBotones = new HBox(10);
        formularioBotones.setPadding(new Insets(0, 20, 10, 20));
        formularioBotones.setAlignment(Pos.CENTER_RIGHT);
        formularioBotones.getChildren().addAll(btnAgregar, btnEliminar);

        TableView<Pedido> tabla = new TableView<>();
        tabla.setPlaceholder(new Label("No hay pedidos registrados."));

        cargarPedidosEnTabla(pedidoControlador.obtenerTodosLosPedidos(), tabla);

        btnNuevo.setOnAction(e -> {
            txtEmail.clear();
            txtCodigo.clear();
            txtCantidad.clear();
            txtNumPedido.clear();
            txtEmail.requestFocus();
        });

        btnAgregar.setOnAction(e -> {
            try {
                String email = txtEmail.getText();
                String codigo = txtCodigo.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());

                Cliente cliente = clienteControlador.obtenerCliente(email);
                Articulo articulo = articuloControlador.obtenerArticulo(codigo);

                if (cliente == null || articulo == null) {
                    throw new IllegalArgumentException("Cliente o artículo no encontrado.");
                }

                if (cantidad <= 0) {
                    throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
                }

                Pedido pedido = new Pedido(cliente, articulo, cantidad, LocalDateTime.now());
                pedidoControlador.agregarPedido(pedido);

                new Alert(Alert.AlertType.INFORMATION, "Pedido agregado correctamente.").showAndWait();

                txtEmail.clear();
                txtCodigo.clear();
                txtCantidad.clear();

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "La cantidad debe ser un número válido.").showAndWait();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).showAndWait();
            }
        });

        btnMostrar.setOnAction(e -> cargarPedidosEnTabla(pedidoControlador.obtenerTodosLosPedidos(), tabla));

        btnEliminar.setOnAction(e -> {
            String input = txtNumPedido.getText().trim();

            if (input.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Debes ingresar un número de pedido.").showAndWait();
                return;
            }

            try {
                int num = Integer.parseInt(input);

                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar eliminación");
                confirmacion.setHeaderText(null);
                confirmacion.setContentText("¿Estás seguro de que deseas eliminar este pedido?");

                confirmacion.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        pedidoControlador.eliminarPedido(num);
                        tabla.getItems().removeIf(p -> p.getNumPedido() == num);
                        new Alert(Alert.AlertType.INFORMATION, "Pedido eliminado correctamente.").showAndWait();
                        txtNumPedido.clear();
                    }
                });

            } catch (ElementoNoEncontradoException | PedidoNoEliminableException ex) {
                new Alert(Alert.AlertType.WARNING, ex.getMessage()).showAndWait();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "El número de pedido debe ser un número entero.").showAndWait();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error inesperado: " + ex.getMessage()).showAndWait();
            }
        });

        btnPendientes.setOnAction(e -> {
            String email = txtEmail.getText().trim();

            if (email.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Debes ingresar el email del cliente.").showAndWait();
                return;
            }

            try {
                var pendientes = pedidoControlador.obtenerPedidosPendientesPorCliente(email);

                if (pendientes.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "No hay pedidos pendientes para este cliente.").showAndWait();
                }

                cargarPedidosEnTabla(pendientes, tabla);

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error al filtrar pedidos pendientes: " + ex.getMessage()).showAndWait();
            }
        });

        btnEnviados.setOnAction(e -> {
            String email = txtEmail.getText();
            if (email.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Debes ingresar el email del cliente.").showAndWait();
                return;
            }

            try {
                var enviados = pedidoControlador.obtenerPedidosEnviadosPorCliente(email);

                if (enviados.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "No hay pedidos enviados para este cliente.").showAndWait();
                }

                cargarPedidosEnTabla(enviados, tabla);

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error al filtrar pedidos enviados: " + ex.getMessage()).showAndWait();
            }
        });

        btnVolver.setOnAction(e -> stage.close());

        sidebar.getChildren().addAll(sidebarTitle, btnNuevo, btnMostrar, btnPendientes, btnEnviados, btnVolver);

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.getChildren().addAll(formulario, formularioBotones, tabla);

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(content);

        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void cargarPedidosEnTabla(java.util.List<Pedido> pedidos, TableView<Pedido> tabla) {
        tabla.getItems().clear();
        tabla.getColumns().clear();

        ObservableList<Pedido> datos = FXCollections.observableArrayList(pedidos);

        TableColumn<Pedido, String> colNum = new TableColumn<>("Pedido #");
        colNum.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getNumPedido())));

        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getCliente().getNombre()));

        TableColumn<Pedido, String> colArticulo = new TableColumn<>("Artículo");
        colArticulo.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getArticulo().getDescripcion()));

        TableColumn<Pedido, String> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getCantidad())));

        TableColumn<Pedido, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getFechaHoraPedido().toString()));

        tabla.getColumns().addAll(colNum, colCliente, colArticulo, colCantidad, colFecha);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setItems(datos);
    }

}
