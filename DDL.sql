DROP DATABASE IF EXISTS developers;

CREATE DATABASE developers;

USE developers;

CREATE TABLE sviluppatore (
    ID INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    `password` VARCHAR(20) NOT NULL,
    nomeCognome VARCHAR(100) NULL,
    stato VARCHAR(20) NOT NULL,
    curriculum BLOB NOT NULL,
    dataNascita DATE NOT NULL
);

CREATE TABLE email (
	indirizzoEmail VARCHAR(20) PRIMARY KEY,
    ID_Sviluppatore INTEGER UNSIGNED NOT NULL,
    -- Un indirizzo email viene eliminato a seguito dell'eliminazione dello sviluppatore cui appartiene
    CONSTRAINT email_sviluppatore FOREIGN KEY (ID_Sviluppatore) REFERENCES sviluppatore (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE telefono (
	numTelefono VARCHAR(15) PRIMARY KEY,
    ID_Sviluppatore INTEGER UNSIGNED NOT NULL,
    -- Un numero di telefono viene eliminato a seguito dell'eliminazione dello sviluppatore cui appartiene
    CONSTRAINT telefono_sviluppatore FOREIGN KEY (ID_Sviluppatore) REFERENCES sviluppatore (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE progetto (
    ID INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nomeProgetto VARCHAR(30) NOT NULL,
    argomento VARCHAR(50) NULL,
    build VARCHAR(10) NULL,
    descrizione TEXT NULL,
    ID_Coordinatore INTEGER UNSIGNED NOT NULL,
    CONSTRAINT nomeProgetto_coordinatore_distinti UNIQUE (nomeProgetto, ID_Coordinatore),
    -- Non è possibile eliminare uno sviluppatore se coordina almeno un progetto
    -- Per farlo, è necessario cambiare coordinatore o eliminare prima il progetto coinvolto
    CONSTRAINT progetto_coordinatore FOREIGN KEY (ID_Coordinatore)
        REFERENCES sviluppatore (ID)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE messaggio (
	ID INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	-- Autore memorizzato mediante username
    -- Il tipo di dato è dunque lo stesso
	autore VARCHAR(20) NOT NULL,
	dataOra TIMESTAMP NOT NULL,
    -- Se TRUE, il messaggio è pubblico, altrimenti privato
	pubblico BOOLEAN NOT NULL,
	contenuto TEXT NOT NULL,
	ID_Progetto INTEGER NOT NULL,
    CONSTRAINT autore_dataOra_distinti UNIQUE (autore, dataOra),
    -- Un messaggio viene cancellato a seguito della rimozione del progetto cui fa riferimento
	CONSTRAINT messaggio_progetto FOREIGN KEY (ID_Progetto)
        REFERENCES progetto (ID)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE skill (
	nome varchar(20) PRIMARY KEY,
    descrizione TINYTEXT NOT NULL,
    tipo VARCHAR(15) NOT NULL
);

CREATE TABLE gerarchia (
	ID_Skill_1 VARCHAR(20) NOT NULL, 
    ID_Skill_2 VARCHAR(20) NOT NULL,
    PRIMARY KEY (ID_Skill_1, ID_Skill_2),
    -- Essendo le skill già presenti nel sistema, non prevediamo cancellazioni
    CONSTRAINT gerarchia_skill_1 FOREIGN KEY (ID_Skill_1) REFERENCES skill (nome)
    ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT gerarchia_skill_2 FOREIGN KEY (ID_Skill_2) REFERENCES skill (nome) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE possiede (
	ID_Sviluppatore INTEGER UNSIGNED,
    ID_Skill VARCHAR(20),
    livello ENUM('1','2','3','4','5','6','7','8','9','10') NOT NULL,
    PRIMARY KEY (ID_Sviluppatore, ID_Skill),
    -- Eliminando uno sviluppatore, perdiamo traccia delle skill che possiede
    CONSTRAINT possiede_sviluppatore FOREIGN KEY (ID_Sviluppatore) REFERENCES sviluppatore (ID) ON DELETE CASCADE ON UPDATE CASCADE,
    -- Essendo già presente nel sistema, prevediamo che nessuna skill venga rimossa dal sistema. Comunque, verifichiamo che nessuno sviluppatore la possieda
    CONSTRAINT possiede_skill FOREIGN KEY (ID_Skill) REFERENCES skill (nome) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE task (
    ID INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descrizione TINYTEXT NULL,
    tipo VARCHAR(15) NOT NULL,
    aperto BOOLEAN NOT NULL,
    -- Attributo ridondante
    -- Non imponiamo che sia diverso da NULL
    numCollaboratori TINYINT NULL DEFAULT 0,
    completamento TIMESTAMP NOT NULL,
    ID_Progetto INTEGER NOT NULL,
    CONSTRAINT nome_progetto_distinti UNIQUE (nome, ID_Progetto),
    -- A seguito dell'eliminazione di un progetto, tutti i relativi task vengono rimossi
    CONSTRAINT task_progetto FOREIGN KEY (ID_Progetto)
        REFERENCES progetto (ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE richiede (
	ID_Skill VARCHAR(20),
    ID_Task INTEGER UNSIGNED,
    livelloMinimo ENUM('1','2','3','4','5','6','7','8','9','10') NOT NULL,
    PRIMARY KEY (ID_Skill, ID_Task),
    -- Essendo già presente nel sistema, prevediamo che nessuna skill venga rimossa dal sistema. Comunque, verifichiamo che nessun task la richieda
    CONSTRAINT richiede_skill FOREIGN KEY (ID_Skill) REFERENCES skill (nome) ON DELETE NO ACTION ON UPDATE CASCADE,
    -- All'eliminazione di un task, perdiamo traccia delle skill che richiede
    CONSTRAINT richiede_task FOREIGN KEY (ID_Task) REFERENCES task (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE partecipa (
	ID_Sviluppatore INTEGER UNSIGNED,
    ID_Task INTEGER UNSIGNED,
    dataInizio TIMESTAMP NOT NULL, 
    PRIMARY KEY (ID_Sviluppatore, ID_Task),
    -- Se rimuoviamo uno sviluppatore, perdiamo anche tutte le sue partecipazioni correnti
    CONSTRAINT partecipa_sviluppatore FOREIGN KEY (ID_Sviluppatore) REFERENCES sviluppatore (ID) ON DELETE CASCADE ON UPDATE CASCADE,
    -- Se perdiamo un task, perdiamo tutte le partecipazioni correnti
    CONSTRAINT partecipa_task FOREIGN KEY (ID_Task) REFERENCES task (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE haPartecipato (
	ID_Sviluppatore INTEGER UNSIGNED,
    ID_Task INTEGER UNSIGNED,
    valutazione ENUM('1', '2', '3', '4', '5') NULL,
    dataInizio TIMESTAMP NOT NULL,
    dataFine TIMESTAMP NOT NULL,
    PRIMARY KEY (ID_Sviluppatore, ID_Task),
    -- Volendo mantenere le partecipazioni ormai terminate, impediamo l'eliminazione di uno sviluppatore se ne ha almeno una nel sistema
    CONSTRAINT haPartecipato_sviluppatore FOREIGN KEY(ID_Sviluppatore) REFERENCES sviluppatore (ID) ON DELETE NO ACTION ON UPDATE CASCADE,
    -- Volendo mantenere le partecipazioni ormai terminate, impediamo l'eliminazione di un task se lo sviluppatore in questione ne ha almeno una nel sistema
    CONSTRAINT haPartecipato_task FOREIGN KEY (ID_Task) REFERENCES task (ID) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE `file` (
	ID INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    -- Attributi ridondanti
    -- Non imponiamo che siano diversi da NULL, in quanto ricalcolabili
    utenteUltimaModifica VARCHAR(20) NULL,
    dataOraUltimaModifica TIMESTAMP NULL,
	ID_Task INTEGER UNSIGNED NOT NULL,
    CONSTRAINT nome_tipo_task_distinti UNIQUE (nome, tipo, ID_Task),
    -- Rimuovendo un task, i file relativi vanno persi
    CONSTRAINT file_task FOREIGN KEY (ID_Task) REFERENCES task (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE versioneFile (
		ID INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
		dataOraCreazione TIMESTAMP NOT NULL,
        utente VARCHAR(20) NOT NULL,
		contenuto LONGBLOB NOT NULL,
		ID_File INTEGER UNSIGNED NOT NULL,
        -- Ammettiamo NULL per la prima versione di ogni file
        -- Un trigger si occupa di correggere eventuali errori nell'inserimento dell'ID della versione precedente
		ID_Versione_Precedente INTEGER UNSIGNED NULL,
        CONSTRAINT dataOraCreazione_file_distinti UNIQUE (dataOraCreazione, ID_File),
        -- Eliminando un file, se ne perdono le versioni
        CONSTRAINT versioneFile_file FOREIGN KEY (ID_File) REFERENCES `file` (ID) ON DELETE CASCADE ON UPDATE CASCADE,
        -- Impediamo l'eliminazione diretta di una versione file che abbia almeno una versione successiva
        CONSTRAINT versioneFile_versione_precedente FOREIGN KEY (ID_Versione_Precedente) REFERENCES versioneFile (ID) ON DELETE NO ACTION ON UPDATE CASCADE
);
