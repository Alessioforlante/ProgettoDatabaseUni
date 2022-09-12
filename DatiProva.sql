-- Chiamo la funzione per inserire uno sviluppatore
SET @id_sviluppatore_1 = aggiungi_sviluppatore('luca','password1', 'Luca Di Donato', 'Italia', ' ', '2000-02-15', 'email@email.it','333333333');
SET @id_sviluppatore_2 = aggiungi_sviluppatore('Marco','password2', 'Marco Rossi', 'Svizzera', ' ', '2010-05-16', 'email@email.com','333333334');
SET @id_sviluppatore_3 = aggiungi_sviluppatore('Francesca','password3', 'Francesca Verdi', 'Italia', ' ', '1985-12-25', 'email@email.it','333333335');

-- telefono/email multiple
CALL aggiungi_email('email2@email.it',@id_sviluppatore_1)
CALL aggiungi_telefono('433333333',@id_sviluppatore_1)
CALL aggiungi_telefono('533333333',@id_sviluppatore_1)
-- non è ammesso  aggiungere più di 3 email/telefoni! 
CALL aggiungi_email('email3@email.it',@id_sviluppatore_3)
CALL aggiungi_email('email4@email.it',@id_sviluppatore_3)
CALL aggiungi_email('email5@email.it',@id_sviluppatore_3)
CALL aggiungi_telefono('633333333',@id_sviluppatore_3)
CALL aggiungi_telefono('733333333',@id_sviluppatore_3)
CALL aggiungi_telefono('833333333',@id_sviluppatore_3)

SET @id_progetto_1 = inserisci_progetto('software1','informatica','Alpha 1.2','software generico',)@id_sviluppatore_1d)
SET @id_progetto_1 = inserisci_progetto('software2','codice','1.0','software generico 2',)@id_sviluppatore_3)

-- skill
-- Skill(Design1,'abilità di design1',design)
-- Skill(Design1,'abilità di design2',design)
-- Skill(Design1,'abilità di design3',design)
-- Skill(Programmazione1,'abilità di programmazione1',programmazione1)
-- Skill(Programmazione2,'abilità di programmazione1',programmazione2)
-- Skill(Programmazione3,'abilità di programmazione3',programmazione3)
-- Skill(Grafica1,'abilità di grafica1',grafica1)
-- Skill(Grafica2,'abilità di grafica2',grafica2)
-- Skill(Grafica3,'abilità di grafica3',grafica3)

-- 