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
import it.univaq.lbd.domain.Progetto;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProgettiController {

	private Connection connection;

	@FXML
	private TextField cercaProgettiTextField;

	@FXML
	private Button cercaProgettiButton;

	@FXML
	private Button piuAttiviButton;

	@FXML
	private Button stagnantiButton;

	@FXML
	private Button tuttiButton;

	@FXML
	private Button indietroButton;

	@FXML
	private TableView<Progetto> progettiTableView;

	@FXML
	private TableColumn<Progetto, String> nomeProgettoTableColumn;

	@FXML
	private TableColumn<Progetto, String> argomentoTableColumn;

	@FXML
	private TableColumn<Progetto, String> descrizioneTableColumn;

	@FXML
	private TableColumn<Progetto, Void> azioniTableColumn;

	public void initialize() {
		nomeProgettoTableColumn.setCellValueFactory(new PropertyValueFactory<Progetto, String>("nomeProgetto"));
		argomentoTableColumn.setCellValueFactory(new PropertyValueFactory<Progetto, String>("argomento"));
		descrizioneTableColumn.setCellValueFactory(new PropertyValueFactory<Progetto, String>("descrizione"));
		azioniTableColumn.setCellFactory(column -> new ProgettiCellFactory());
	}

	public void setConnection(Connection c) {
		this.connection = c;
	}

	@FXML
	private void cercaProgetti(ActionEvent event) throws ApplicationException {
		try (PreparedStatement s = this.connection.prepareStatement(
				"SELECT p.nomeProgetto, p.argomento, p.descrizione, p.build FROM progetto p WHERE p.nomeProgetto LIKE ?")) {
			s.setString(1, "%" + cercaProgettiTextField.getText() + "%");
			// Creo una lista in cui inserire i risultati
			List<Progetto> risultato = new ArrayList<>();
			try (ResultSet rs = s.executeQuery()) {
				// Itero sui risultati ottenuti
				while (rs.next()) {
					risultato.add(new Progetto(rs.getString("nomeProgetto"), rs.getString("argomento"),
							rs.getString("descrizione"), rs.getString("build")));
				}
				progettiTableView.getItems().setAll(risultato);
			}
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	@FXML
	private void tuttiIProgetti() throws ApplicationException {
		try (Statement s = this.connection.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM progetto");) {
			// Creo una lista in cui inserire i risultati
			List<Progetto> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Progetto(rs.getString("nomeProgetto"), rs.getString("argomento"),
						rs.getString("descrizione"), rs.getString("build")));
			}
			progettiTableView.getItems().setAll(risultato);
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	// TODO: Correggere la query!
	@FXML
	private void progettiPiuAttivi() throws ApplicationException {
		try (Statement s = this.connection.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM progetto")) {
			// Creo una lista in cui inserire i risultati
			List<Progetto> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Progetto(rs.getString("nomeProgetto"), rs.getString("argomento"),
						rs.getString("descrizione"), rs.getString("build")));
			}
			progettiTableView.getItems().setAll(risultato);
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	// TODO: Correggere la query!
	@FXML
	private void progettiStagnanti() throws ApplicationException {
		try (Statement s = this.connection.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM progetto")) {
			// Creo una lista in cui inserire i risultati
			List<Progetto> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Progetto(rs.getString("nomeProgetto"), rs.getString("argomento"),
						rs.getString("descrizione"), rs.getString("build")));
			}
			progettiTableView.getItems().setAll(risultato);
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

class ProgettiCellFactory extends TableCell<Progetto, Void> {
	private final HBox container;

	{
		Button primoBottone = new Button("Progetti");

		primoBottone.setOnAction(event -> {
			// Faccio qualcosa al click del primo bottone
			System.out.println("Primo bottone");
		});

		container = new HBox(5, primoBottone);
	}

	@Override
	public void updateItem(Void item, boolean empty) {
		super.updateItem(item, empty);
		setGraphic(empty ? null : container);
	}

}
