package org.javinity.vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.javinity.controladores.ClienteControlador;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.ClienteEstandar;
import org.javinity.modelos.ClientePremium;

public class ClienteVista {
    private final ClienteControlador clienteControlador;

    public ClienteVista(ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Clientes");

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #2e2e2e;");
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label sidebarTitle = new Label("GESTIÓN DE CLIENTES");
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
        btnGuardar.getStyleClass().add("button");
        btnEliminar.getStyleClass().add("danger-button");

        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(20));

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email del cliente");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");

        TextField txtDomicilio = new TextField();
        txtDomicilio.setPromptText("Domicilio");

        TextField txtNif = new TextField();
        txtNif.setPromptText("NIF");

        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Estándar", "Premium");
        comboTipo.setPromptText("Tipo de cliente");

        formulario.getChildren().addAll(txtEmail, txtNombre, txtDomicilio, txtNif, comboTipo);

        HBox formularioBotones = new HBox(10);
        formularioBotones.setPadding(new Insets(0, 20, 10, 20));
        formularioBotones.setAlignment(Pos.CENTER_RIGHT);
        formularioBotones.getChildren().addAll(btnGuardar, btnEliminar);

        TableView<Cliente> tabla = new TableView<>();
        tabla.setPlaceholder(new Label("No hay clientes registrados."));

        btnNuevo.setOnAction(e -> {
            txtEmail.clear();
            txtNombre.clear();
            txtDomicilio.clear();
            txtNif.clear();
            comboTipo.getSelectionModel().clearSelection();
            txtEmail.requestFocus();
        });

        btnGuardar.setOnAction(e -> {
            try {
                String email = txtEmail.getText().trim();
                String nombre = txtNombre.getText().trim();
                String domicilio = txtDomicilio.getText().trim();
                String nif = txtNif.getText().trim();
                String tipo = comboTipo.getValue();

                if (email.isEmpty() || nombre.isEmpty() || domicilio.isEmpty() || nif.isEmpty() || tipo == null) {
                    throw new IllegalArgumentException("Por favor, introduce todos los datos del cliente.");
                }

                Cliente nuevo = tipo.equals("Premium") ?
                        new ClientePremium(email, nombre, domicilio, nif) :
                        new ClienteEstandar(email, nombre, domicilio, nif);

                clienteControlador.agregarCliente(nuevo);
                new Alert(Alert.AlertType.INFORMATION, "Cliente guardado correctamente.").showAndWait();

                txtEmail.clear(); txtNombre.clear(); txtDomicilio.clear(); txtNif.clear(); comboTipo.getSelectionModel().clearSelection();

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).showAndWait();
            }
        });

        btnMostrar.setOnAction(e -> {
            ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteControlador.obtenerTodosLosClientes());

            TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
            colEmail.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getEmail()));

            TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getNombre()));

            TableColumn<Cliente, String> colDomicilio = new TableColumn<>("Domicilio");
            colDomicilio.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getDomicilio()));

            TableColumn<Cliente, String> colNif = new TableColumn<>("NIF");
            colNif.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getNif()));

            TableColumn<Cliente, String> colTipo = new TableColumn<>("Tipo");
            colTipo.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(
                    p.getValue() instanceof ClientePremium ? "Premium" : "Estándar"
            ));

            tabla.getColumns().setAll(colEmail, colNombre, colDomicilio, colNif, colTipo);
            tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tabla.setItems(clientes);
        });

        btnEliminar.setOnAction(e -> {
            Cliente seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                new Alert(Alert.AlertType.WARNING, "Debes seleccionar un cliente para eliminarlo.").showAndWait();
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar el cliente seleccionado?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    clienteControlador.eliminarCliente(seleccionado);
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
