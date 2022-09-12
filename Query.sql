-- Operazione 1
-- Lista di tutti i progetti attivi nel sistema (cioè con task ancora aperti)

SELECT DISTINCT p.ID, p.nomeProgetto, p.argomento, p.descrizione
FROM progetto p JOIN task t ON (p.ID = t.ID_Progetto)
WHERE t.aperto = true;


-- Operazione 2
-- Inserimento di un nuovo progetto e dei suoi task
-- Implementata mediante procedure


-- Operazione 3
-- Individuazione dei progetti più attivi, sulla base delle ultime modifiche ai loro files
-- Implementata mediante procedure


-- Operazione 4
-- Individuazione dei progetti stagnanti, cioè con task attivi tra i cui file l’ultima modifica risalga a più di un anno addietro.
-- Implementata mediante procedure


-- Operazione 5
-- Ricerca di sviluppatori sulla base di un insieme di skill e opzionalmente con un livello minimo di competenza per ciascuna di esse. Per semplicità, potete limitarvi a una o due skill.
-- Implementata mediante procedure


-- Operazione 6
-- Ricerca di progetti per parola chiave (nel nome o nella descrizione).
-- Implementata mediante procedure


-- Operazione 7
-- Inserimento di uno sviluppatore.
-- Implementata mediante procedure


-- Operazione 8
-- Aggiornamento delle skill di uno sviluppatore.
-- Implementata mediante procedure


-- Operazione 9
-- Inserimento di  una  valutazione  per  uno  sviluppatore  relativamente a un task a cui ha collaborato.
-- Implementata mediante procedure


-- Operazione 10
-- Generazione del “curriculum” di uno sviluppatore, cioè della lista dei progetti/task a cui ha lavorato o lavora. Se il task è terminato, tale lista includerà anche la valutazione dello sviluppatore data dal coordinatore del progetto.
-- Implementata mediante procedure


-- Operazione 11
-- Modifica di un progetto: variazione delle date associate a un task
-- Implementata mediante procedure


-- Operazione 12
-- Esclusione di uno sviluppatore da un task a cui sta correntemente partecipando.
-- Implementata mediante procedure


-- Operazione 13
-- Ripristino della versione precedente (in ordine temporale) di un file sorgente.
-- Implementata mediante procedure


-- Operazione 14
-- Generazione della storia delle modifiche a un file sorgente: data e nome dell’utente che ha effettuato le modifiche.
-- Implementata mediante procedure
