package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	
	public Model() {
		corsoDao = new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(Integer periodo) {
		return this.corsoDao.getCorsiByPeriodo(periodo);
	}
	
	public Map<Corso, Integer> getTotIscrittiCorsiByPeriodo(Integer periodo) {
		return this.corsoDao.getTotIscrittiCorsiByPeriodo(periodo);
	}
	
	public List<Studente> getStudentiByCorso(String codice) {
		return this.corsoDao.getStudentiByCorso(new Corso(codice, null, null, null));
	}

	public boolean esisteCorso(String codice) {
		return this.corsoDao.esisteCorso(new Corso(codice, null, null, null));
	}
	
	/* RIUSCIAMO A RISOLVERLO SENZA UNA QUERY ma è meglio USARE DATABASEEEEEEE
	public Map<String, Integer> getDivisioneCDS(String codice) {
		
		// dato un corso vogliamo il numero di studenti del corso che appartengono a dipartimenti diversi
		// es: GEST -> 50, INF -> 30, MAT -> 20
		
		Map<String, Integer> divisione = new HashMap<>();
		
		// otteniamo elenco studenti dato il corso
		List<Studente> studenti = this.getStudentiByCorso(codice);
		
		for(Studente s : studenti) {
			if(s.getcDS()== null || s.getcDS().isEmpty())
				break;
			// se il dipartimento non esiste nella mappa, aggiungiamo e settiamo 1 studente
			if(!divisione.containsKey(s.getcDS()))
				divisione.put(s.getcDS(), 1);
			else // se esiste già, dobbiamo incrementare di 1 il numero degli studenti già dentro la mappa
				// dato il dipartimento con divisione.get otteniamo il numero studenti e poi + 1
				divisione.put(s.getcDS(), divisione.get(s.getcDS()) +1);
		
		}
		
		return divisione;

	} */
	
	// conviene sempre ottenere le cose dal database!!!!
	public Map<String, Integer> getDivisioneCDS(String codice) {
		return this.corsoDao.getDivisioneCDS(codice);
		
		
	}
}
