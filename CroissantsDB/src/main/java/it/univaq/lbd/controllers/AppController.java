
package it.univaq.lbd.controllers;

import java.io.IOException;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AppController {

	private Connection connection;

	@FXML
	private Label label;

	@FXML
	private Button creaProgettoButton;

	@FXML
	private Button mieiProgettiButton;

	@FXML
	private Button tuttiProgettiButton;

	@FXML
	private Button sviluppatoriButton;

	@FXML
	private Button indietroButton;

	public void initialize() {

	}

	public void setConnection(Connection c) {
		this.connection = c;
	}

	@FXML
	private void switchToCreaProgetto(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\crea_progetto.fxml"));
			Parent root = loader.load();
			CreaProgettoController controller = (CreaProgettoController) loader.getController();
			controller.setConnection(this.connection);
			Stage stageCorrente = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stageCorrente.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void switchToMieiProgetti(ActionEvent event) {
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

	@FXML
	private void switchToProgetti(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\progetti.fxml"));
			Parent root = loader.load();
			ProgettiController controller = (ProgettiController) loader.getController();
			controller.setConnection(this.connection);
			Stage stageCorrente = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stageCorrente.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void switchToSviluppatori(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\sviluppatori.fxml"));
			Parent root = loader.load();
			SviluppatoriController controller = (SviluppatoriController) loader.getController();
			controller.setConnection(this.connection);
			controller.initializeData();
			Stage stageCorrente = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stageCorrente.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}