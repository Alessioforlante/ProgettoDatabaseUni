package it.univaq.lbd.domain;

import javafx.beans.property.SimpleStringProperty;

public class Sviluppatore {
	private final SimpleStringProperty username = new SimpleStringProperty("");
	private final SimpleStringProperty nomeCognome = new SimpleStringProperty("");

	public Sviluppatore() {
		this("", "");
	}

	public Sviluppatore(String username, String nomeCognome) {
		this.setUsername(username);
		this.setNomeCognome(nomeCognome);
	}

	public String getUsername() {
		return this.username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public String getNomeCognome() {
		return this.nomeCognome.get();
	}

	public void setNomeCognome(String nomeCognome) {
		this.nomeCognome.set(nomeCognome);
	}
}
