package it.univaq.lbd.controllers;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import it.univaq.lbd.ApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ModificaDataController {
	private Connection connection;
	private int taskID;

	@FXML
	private DatePicker dataDatePicker;

	public void initialize() {

	}

	public void setConnection(Connection c) {
		this.connection = c;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	// Popolare il metodo!
	@FXML
	private void modificaData(ActionEvent event) throws ApplicationException {
		try (CallableStatement s = this.connection.prepareCall("{call modifica_date_task(?,?)}")) {
			s.setInt(1, this.taskID);
			s.setTimestamp(2, Timestamp.valueOf(dataDatePicker.getValue().atStartOfDay()));
			s.execute();
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	@FXML
	private void vaiIndietro(ActionEvent event) {
		System.out.println("Task ID passato " + this.taskID + "\n");
		System.out.println("Connessione " + this.connection + "\n");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\miei_progetti.fxml"));
			Parent root = loader.load();
			MieiProgettiController controller = (MieiProgettiController) loader.getController();
			controller.setConnection(this.connection);
			controller.initializeData();
			Stage stageCorrente = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stageCorrente.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
