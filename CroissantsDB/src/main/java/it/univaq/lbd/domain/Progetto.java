package it.univaq.lbd.domain;

import javafx.beans.property.SimpleStringProperty;

// TODO: controllare la definizione della classe
public class Progetto {
	private final SimpleStringProperty nomeProgetto = new SimpleStringProperty("");
	private final SimpleStringProperty argomento = new SimpleStringProperty("");
	private final SimpleStringProperty descrizione = new SimpleStringProperty("");
	private final SimpleStringProperty build = new SimpleStringProperty("");

	public Progetto() {
		this("", "", "", "");
	}

	public Progetto(String nomeProgetto, String argomento, String descrizione, String build) {
		this.setNomeProgetto(nomeProgetto);
		this.setArgomento(argomento);
		this.setDescrizione(descrizione);
		this.setBuild(build);
	}

	public String getNomeProgetto() {
		return this.nomeProgetto.get();
	}

	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto.set(nomeProgetto);
	}

	public String getArgomento() {
		return this.argomento.get();
	}

	public void setArgomento(String argomento) {
		this.argomento.set(argomento);
	}

	public String getDescrizione() {
		return this.descrizione.get();
	}

	public void setDescrizione(String descrizione) {
		this.descrizione.set(descrizione);
	}

	public String getBuild() {
		return this.build.get();
	}

	public void setBuild(String build) {
		this.build.set(build);
	}
}