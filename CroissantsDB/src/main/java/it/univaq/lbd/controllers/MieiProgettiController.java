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
import it.univaq.lbd.domain.Sviluppatore;
import it.univaq.lbd.domain.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MieiProgettiController {

	private Connection connection;

	@FXML
	private TableView<Progetto> mieiProgettiTableView;

	@FXML
	private TableColumn<Progetto, String> nomeProgettoTableColumn;

	@FXML
	private TableColumn<Progetto, String> buildTableColumn;

	@FXML
	private TableView<Sviluppatore> mieiSviluppatoriTableView;

	@FXML
	private TableColumn<Sviluppatore, String> usernameTableColumn;

	@FXML
	private TableColumn<Sviluppatore, String> nomeCognomeTableColumn;

	@FXML
	private TableColumn<Sviluppatore, Void> azioniSviluppatoreTableColumn;

	@FXML
	private TableView<Task> taskTableView;

	@FXML
	private TableColumn<Task, String> nomeTaskTableColumn;

	@FXML
	private TableColumn<Task, String> descrizioneTableColumn;

	@FXML
	private TableColumn<Task, Void> azioniTaskTableColumn;

	public void initialize() {
		nomeProgettoTableColumn.setCellValueFactory(new PropertyValueFactory<Progetto, String>("nomeProgetto"));
		buildTableColumn.setCellValueFactory(new PropertyValueFactory<Progetto, String>("build"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<Sviluppatore, String>("username"));
		nomeCognomeTableColumn.setCellValueFactory(new PropertyValueFactory<Sviluppatore, String>("nomeCognome"));
		nomeTaskTableColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("nomeTask"));
		descrizioneTableColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("descrizione"));
		azioniSviluppatoreTableColumn.setCellFactory(column -> new MieiProgettiSviluppatoreCellFactory());
		azioniTaskTableColumn.setCellFactory(column -> new MieiProgettiTaskCellFactory());

		mieiProgettiTableView.setRowFactory(tv -> {
			TableRow<Progetto> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					Progetto progetto = row.getItem();
					try {
						this.sviluppatoriPartecipanti(progetto.getNomeProgetto());
						this.taskAssociati(progetto.getNomeProgetto());
					} catch (ApplicationException e) {
						e.printStackTrace();
					}
				}
			});
			return row;
		});

	}

	public void initializeData() {
		try {
			mieiProgettiTableView.getItems().setAll(this.cercaMieiProgetti());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public void setConnection(Connection c) {
		this.connection = c;
	}

	private List<Progetto> cercaMieiProgetti() throws ApplicationException {
		try (Statement s = this.connection.createStatement();
				ResultSet rs = s.executeQuery("SELECT * FROM progetto p WHERE p.ID_Coordinatore = 1")) {
			// Creo una lista in cui inserire i risultati
			List<Progetto> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Progetto(rs.getString("nomeProgetto"), rs.getString("argomento"),
						rs.getString("descrizione"), rs.getString("build")));
			}
			return risultato;
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	private void sviluppatoriPartecipanti(String nomeProgetto) throws ApplicationException {
		try (PreparedStatement s = this.connection.prepareStatement(
				"SELECT DISTINCT s.username, s.nomeCognome FROM progetto p JOIN task t ON (p.ID = t.ID_Progetto) JOIN partecipa pa ON (pa.ID_Task = t.ID) JOIN sviluppatore s ON (s.ID = pa.ID_Sviluppatore) WHERE p.nomeProgetto = ?")) {
			s.setString(1, nomeProgetto);
			ResultSet rs = s.executeQuery();
			// Creo una lista in cui inserire i risultati
			List<Sviluppatore> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Sviluppatore(rs.getString("username"), rs.getString("nomeCognome")));
			}
			mieiSviluppatoriTableView.getItems().setAll(risultato);
		} catch (SQLException ex) {
			throw new ApplicationException("Errore di esecuzione della query", ex);
		}
	}

	private void taskAssociati(String nomeProgetto) throws ApplicationException {
		try (PreparedStatement s = this.connection.prepareStatement(
				"SELECT t.* FROM progetto p JOIN task t ON (p.ID = t.ID_Progetto) WHERE p.nomeProgetto = ?")) {
			s.setString(1, nomeProgetto);
			ResultSet rs = s.executeQuery();
			// Creo una lista in cui inserire i risultati
			List<Task> risultato = new ArrayList<>();
			// Itero sui risultati ottenuti
			while (rs.next()) {
				risultato.add(new Task(rs.getString("nome"), rs.getString("descrizione")));
			}
			taskTableView.getItems().setAll(risultato);
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

	class MieiProgettiTaskCellFactory extends TableCell<Task, Void> {
		private final HBox container;

		{
			Button modificaDataButton = new Button("Modifica Data");

			modificaDataButton.setOnAction(event -> {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\modifica_data.fxml"));
					Parent root = loader.load();
					ModificaDataController controller = (ModificaDataController) loader.getController();
					controller.setConnection(connection);
					// Passare l'ID corretto
					// Per ora c'è un placeholder casuale
					controller.setTaskID(1);
					Stage stageCorrente = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stageCorrente.getScene().setRoot(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			container = new HBox(5, modificaDataButton);
		}

		@Override
		public void updateItem(Void item, boolean empty) {
			super.updateItem(item, empty);
			setGraphic(empty ? null : container);
		}

	}

	class MieiProgettiSviluppatoreCellFactory extends TableCell<Sviluppatore, Void> {
		private final HBox container;

		{
			Button escludiButton = new Button("Escludi");

			escludiButton.setOnAction(event -> {
				System.out.println(getTableRow().getItem().getUsername());
				// Chiamare procedura opportuna
			});

			container = new HBox(5, escludiButton);
		}

		@Override
		public void updateItem(Void item, boolean empty) {
			super.updateItem(item, empty);
			setGraphic(empty ? null : container);
		}

	}

}
