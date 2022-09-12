package it.univaq.lbd.domain;

import javafx.beans.property.SimpleStringProperty;

public class Task {
	private final SimpleStringProperty nomeTask = new SimpleStringProperty("");
	private final SimpleStringProperty descrizione = new SimpleStringProperty("");

	public Task() {
		this("", "");
	}

	public Task(String nomeTask, String descrizione) {
		this.setNomeTask(nomeTask);
		this.setDescrizione(descrizione);
	}

	public String getNomeTask() {
		return this.nomeTask.get();
	}

	public void setNomeTask(String nomeTask) {
		this.nomeTask.set(nomeTask);
	}

	public String getDescrizione() {
		return this.descrizione.get();
	}

	public void setDescrizione(String descrizione) {
		this.descrizione.set(descrizione);
	}
}
