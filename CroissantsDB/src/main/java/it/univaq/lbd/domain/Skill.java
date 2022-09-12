package it.univaq.lbd.domain;

import javafx.beans.property.SimpleStringProperty;

public class Skill {
	private final SimpleStringProperty nome = new SimpleStringProperty("");
	private final SimpleStringProperty descrizione = new SimpleStringProperty("");
	private final SimpleStringProperty tipo = new SimpleStringProperty("");

	public Skill() {
		this("", "", "");
	}

	public Skill(String nome, String descrizione, String tipo) {
		this.setNome(nome);
		this.setDescrizione(descrizione);
		this.setTipo(tipo);
	}

	public String getNome() {
		return this.nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}

	public String getDescrizione() {
		return this.descrizione.get();
	}

	public void setDescrizione(String descrizione) {
		this.descrizione.set(descrizione);
	}

	public String getTipo() {
		return this.tipo.get();
	}

	public void setTipo(String tipo) {
		this.tipo.set(tipo);
	}
}
