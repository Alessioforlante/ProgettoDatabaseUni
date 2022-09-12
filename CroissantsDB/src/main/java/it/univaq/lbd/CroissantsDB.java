package it.univaq.lbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.univaq.lbd.controllers.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CroissantsDB extends Application {
	private static final String DB_NAME = "developers";
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME
			+ "?noAccessToProcedureBodies=true" + "&serverTimezone=Europe/Rome";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "admin";

	// Il nome del driver utilizzato
	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	private Connection c;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.c = connect();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
			Parent root = loader.load();
			AppController controller = (AppController) loader.getController();
			// Passo la connessione appena ottenuta al controller
			// per effettuare le query di cui ho bisogno
			controller.setConnection(c);
			primaryStage.setTitle("CroissantsDB");
			primaryStage.setScene(new Scene(root, 800, 600));
			primaryStage.show();
		} catch (ApplicationException e) {
			System.out.println("Eccezione!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
		try {
			c.close();
			System.out.println("Connessione chiusa!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static Connection connect() throws ApplicationException {
		try {
			// La riga seguente è ormai superflua
			Class.forName(DRIVER_NAME);
			return DriverManager.getConnection(CONNECTION_STRING, DB_USER, DB_PASSWORD);
		} catch (SQLException | ClassNotFoundException ex) {
			// Sollevo l'eccezione custom ApplicationException
			throw new ApplicationException("Errore di connessione", ex);
		}
	}

}
