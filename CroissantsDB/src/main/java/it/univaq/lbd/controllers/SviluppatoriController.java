package it.univaq.lbd.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.univaq.lbd.ApplicationException;
import it.univaq.lbd.domain.Skill;
import it.univaq.lbd.domain.Sviluppatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SviluppatoriController {

	private Connection connection;

	@FXML
	private TextField ricercaSviluppatoriTextField;

	@FXML
	private Button cercaButton;

	@FXML
	private TableView<Skill> skillTableView;

	@FXML
	private TableColumn<Skill, String> skillTableColumn;

	@FXML
	private TableColumn<Skill, Boolean> richiestaTableColumn;

	@FXML
	private TableColumn<Skill, String> livelloMinimoTableColumn;

	@FXML
	private TableView<Sviluppatore> sviluppatoriTableView;

	@FXML
	private TableColumn<Sviluppatore, String> usernameTableColumn;

	@FXML
	private TableColumn<Sviluppatore, Void> azioniTableColumn;

	public void initialize() {
		skillTableView.setEditable(true);
		skillTableColumn.setCellValueFactory(new PropertyValueFactory<Skill, String>("nome"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<Sviluppatore, String>("username"));
		richiestaTableColumn.setCellFactory(column -> new CheckBoxTableCell<Skill, Boolean>());
		livelloMinimoTableColumn.setCellFactory(column -> new TextFieldTableCell<Skill, String>());
		azioniTableColumn.setCellFactory(column -> new SviluppatoriCellFactory());
	}

	public void initializeData() {
		try {
			skillTableView.getItems().setAll(this.tutteLeSkill());
			sviluppatoriTableView.getItems().setAll(this.tuttiGliSviluppatori());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public void setConnection(Connection c) {
		this.connection = c;
	}

	private List<Skill> tutteLeSkill() throws ApplicationException {
		try (Statement s = this.connection.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM skill")) {
			// Creo una lista in cui inserire i risultati
			List<Skill> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Skill(rs.getString("nome"), rs.getString("descrizione"), rs.getString("tipo")));
			}
			return risultato;
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	private List<Sviluppatore> tuttiGliSviluppatori() throws ApplicationException {
		try (Statement s = this.connection.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM sviluppatore")) {
			// Creo una lista in cui inserire i risultati
			List<Sviluppatore> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Sviluppatore(rs.getString("username"), rs.getString("nomeCognome")));
			}
			return risultato;
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	@FXML
	private void cercaSviluppatori(ActionEvent event) throws ApplicationException {
		try (PreparedStatement s = this.connection
				.prepareStatement("SELECT s.username, s.nomeCognome FROM sviluppatore s WHERE s.username LIKE ?")) {
			s.setString(1, "%" + ricercaSviluppatoriTextField.getText() + "%");
			// Creo una lista in cui inserire i risultati
			List<Sviluppatore> risultato = new ArrayList<>();
			try (ResultSet rs = s.executeQuery()) {
				// Itero sui risultati ottenuti
				while (rs.next()) {
					risultato.add(new Sviluppatore(rs.getString("username"), rs.getString("nomeCognome")));
				}
				sviluppatoriTableView.getItems().setAll(risultato);
			}
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

class SviluppatoriCellFactory extends TableCell<Sviluppatore, Void> {
	private final HBox container;

	{
		Button primoBottone = new Button("Add");
		Button secondoBottone = new Button("View");
		Button terzoBottone = new Button("Delete");

		primoBottone.setOnAction(event -> {
			// Faccio qualcosa al click del primo bottone
			System.out.println("Primo bottone");
		});

		secondoBottone.setOnAction(event -> {
			// Faccio qualcosa al click del secondo bottone
			System.out.println("Secondo bottone");
			// viewProduct(getTableRow().getItem());
		});

		terzoBottone.setOnAction(event -> {
			// Faccio qualcosa al click del terzo bottone
			System.out.println("Terzo bottone");
			// getTableView().getItems().remove(getIndex());
		});

		container = new HBox(5, primoBottone, secondoBottone, terzoBottone);
	}

	@Override
	public void updateItem(Void item, boolean empty) {
		super.updateItem(item, empty);
		setGraphic(empty ? null : container);
	}

}
