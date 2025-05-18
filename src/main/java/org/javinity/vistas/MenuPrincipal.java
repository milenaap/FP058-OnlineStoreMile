package org.javinity.vistas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {

    public static ArticuloVista articuloVista;
    public static ClienteVista clienteVista;
    public static PedidoVista pedidoVista;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Online Store - Menú Principal");

        BorderPane root = new BorderPane();

        // Sidebar (Menú lateral izquierdo)
        VBox sidebar = new VBox(15);
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2e2e2e;");
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label menuTitle = new Label("MENÚ");
        menuTitle.getStyleClass().add("title");

        Button btnArticulos = new Button("Gestión de Artículos");
        Button btnClientes = new Button("Gestión de Clientes");
        Button btnPedidos = new Button("Gestión de Pedidos");
        Button btnSalir = new Button("Salir");

        btnArticulos.getStyleClass().add("nav-button");
        btnClientes.getStyleClass().add("nav-button");
        btnPedidos.getStyleClass().add("nav-button");
        btnSalir.getStyleClass().add("nav-button");

        btnArticulos.setMaxWidth(Double.MAX_VALUE);
        btnClientes.setMaxWidth(Double.MAX_VALUE);
        btnPedidos.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setMaxWidth(Double.MAX_VALUE);

        btnArticulos.setOnAction(e -> articuloVista.mostrar());
        btnClientes.setOnAction(e -> clienteVista.mostrar());
        btnPedidos.setOnAction(e -> pedidoVista.mostrar());
        btnSalir.setOnAction(e -> primaryStage.close());

        sidebar.getChildren().addAll(menuTitle, btnArticulos, btnClientes, btnPedidos, btnSalir);

        // Centro visual
        VBox centerContent = new VBox();
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setStyle("-fx-background-color: #f4f4f4;");
        centerContent.setPadding(new Insets(20));

        //Logo
        Image logo = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);  // puedes ajustar el tamaño si es muy grande
        logoView.setPreserveRatio(true);

        primaryStage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toExternalForm()));

        Label bienvenida = new Label("MENÚ PRINCIPAL");
        bienvenida.getStyleClass().add("title");

        centerContent.getChildren().addAll(logoView, bienvenida);

        root.setLeft(sidebar);
        root.setCenter(centerContent);

        Scene scene = new Scene(root, 700, 450);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
