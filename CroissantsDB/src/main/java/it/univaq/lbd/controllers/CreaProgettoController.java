package it.univaq.lbd.controllers;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import it.univaq.lbd.ApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreaProgettoController {

	private Connection connection;

	@FXML
	private TextField nomeProgettoTextField;

	@FXML
	private TextField descrizioneTextField;

	@FXML
	private TextField buildTextField;

	@FXML
	private TextField argomentoTextField;

	@FXML
	private Button inserisciButton;

	@FXML
	private Button indietroButton;

	public void initialize() {

	}

	public void setConnection(Connection c) {
		this.connection = c;
	}

	// TODO: Completare!
	@FXML
	private void inserisciProgetto(ActionEvent event) throws ApplicationException {
		try (CallableStatement s = this.connection.prepareCall("{call inserisci_progetto_task(?,?,?,?,?,?)}")) {
			s.setInt(1, 10);
			s.setString(2, nomeProgettoTextField.getText());
			s.setString(3, argomentoTextField.getText());
			s.setString(4, buildTextField.getText());
			s.setString(5, descrizioneTextField.getText());
			s.setInt(6, 1);
			s.execute();
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	@FXML
	private void vaiIndietro(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\main.fxml"));
			Parent root = loader.load();
			AppController controller = (AppController) loader.getController();
			controller.setConnection(this.connection);
			Stage stageCorrente = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stageCorrente.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}