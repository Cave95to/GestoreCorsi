/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPeriodo"
    private TextField txtPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorso"
    private TextField txtCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorsiPerPeriodo"
    private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNumeroStudenti"
    private Button btnNumeroStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudenti"
    private Button btnStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnDivisioneStudenti"
    private Button btnDivisioneStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
    	
    	txtRisultato.clear();
    	
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo;
    	
//    	if(txtPeriodo.getText()==null) {
//    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
//    		return;
//    	}
    	
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch(NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	} /* catch(NullPointerException npe){
    		txtRisultato.setText("Devi inserire!!! un numero (1 o 2) per il periodo didattico");
    		return;
    	} */
    	
    	if (periodo<1 || periodo>2) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}
    	
    	List<Corso> corsi = this.model.getCorsiByPeriodo(periodo);
    	
    	/* non mi piace come stampa
    	for (Corso c : corsi)
    		this.txtRisultato.appendText(c.toString()+"\n");
    	*/
    	
    	// implementiamo string builder per formattare la stampa, ci crea la stringa
    	StringBuilder sb = new StringBuilder();
    	
    	// necessario perche sia davvero allineato e formattato
    	this.txtRisultato.setStyle("-fx-font-family: monospace");
    	
    	for (Corso c : corsi) {
    		// TUTTE QUESTE INFO LE METTE SULLA STESSA RIGA!
    		// %creiamo un place holder = una colonna di 8 caratteri, - = allineati a sinistra, s = di stringhe, +SPAZIO
    		sb.append(String.format("%-8s ", c.getCodins()));
    		// d = digit, al massimo 4 caratteri.. sempre SPAZIO per separare elementi
    		sb.append(String.format("%-4d ", c.getCrediti()));
    		sb.append(String.format("%-50s ", c.getNome()));
    		// aggiungere a capo dopo ultimo elemento della riga
    		sb.append(String.format("%-4d\n", c.getPd()));
    	}
    	
    	this.txtRisultato.appendText(sb.toString());
    }

    @FXML
    void numeroStudenti(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo;
    	
//    	if(txtPeriodo.getText()==null) {
//    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
//    		return;
//    	}
    	
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch(NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}/* catch(NullPointerException npe){
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}*/
    	
    	if (periodo<1 || periodo>2) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return;
    	}
    	
    	Map<Corso,Integer> corsi = this.model.getTotIscrittiCorsiByPeriodo(periodo);
    	
    	/*
    	for (Corso c : corsi.keySet()) {
    		this.txtRisultato.appendText(c.toString());
    		Integer n = corsi.get(c);
    		this.txtRisultato.appendText("\t"+ n + "\n");
    	}
    	*/	
    	
    	//modifichiamo stampa
    	StringBuilder sb = new StringBuilder();
    	
    	this.txtRisultato.setStyle("-fx-font-family: monospace");
    	
    	for(Corso c : corsi.keySet()) 
    		sb.append(String.format("%-50s %4d\n", c.getNome(), corsi.get(c)));

    	txtRisultato.appendText(sb.toString());
    	
    	
    }

    @FXML
    void stampaDivisione(ActionEvent event) {
    	
    	txtRisultato.clear();
    	
    	String codice = txtCorso.getText();
    	
    	if (codice.isEmpty()) {
    		txtRisultato.appendText("Inserire il codice di un corso");
    		return;
    	}
    	
    	// ----> AGGIUNGIAMO CONTROLLO VERIFICA SE CODICE INSERITO ESISTE
    	if(!model.esisteCorso(codice)) {
    		txtRisultato.appendText("Il corso non esiste");
    		return;
    	}
    	
    	Map<String,Integer> divisione = model.getDivisioneCDS(codice);
    	
    	/* miglioriamo stampa
    	for(String cds : divisione.keySet()) {
    		
    		txtRisultato.appendText(cds + " " + divisione.get(cds) + "\n");
    	}
    	*/
    	
    	for(String cds : divisione.keySet())
    		
    		txtRisultato.appendText(String.format("%-8s %4d\n", cds,divisione.get(cds)));
    }

    @FXML
    void stampaStudenti(ActionEvent event) {
    	
    	txtRisultato.clear();
    	
    	String codice = txtCorso.getText();
    	
    	if (codice.isEmpty()) {
    		txtRisultato.appendText("Inserire il codice di un corso");
    		return;
    	}
    	
    	// ----> AGGIUNGIAMO CONTROLLO VERIFICA SE CODICE INSERITO ESISTE
    	if(!model.esisteCorso(codice)) {
    		txtRisultato.appendText("Il corso non esiste");
    		return;
    	}
    	
    	List<Studente> studenti = model.getStudentiByCorso(codice);
    	
    	// IN QUESTO MODO NON POSSIAMO DISTINGUERE SE NON CI SONO STUDENTI PERCHE' IL CORSO E' NUOVO OPPURE SE PERCHE'
    	// IL CODICE NON ESISTE
    	if(studenti.size()==0) {
    		txtRisultato.appendText("Il corso non ha iscritti");
    		return;
    	}
    	
    	/* miglioriamo stampa
    	for(Studente s : studenti) {
    		txtRisultato.appendText(s + "\n");
    	}
    	*/
    	
    	StringBuilder sb = new StringBuilder();
    	
    	// necessario perche sia davvero allineato e formattato
    	this.txtRisultato.setStyle("-fx-font-family: monospace");
    	
    	for (Studente s : studenti) {
    		// TUTTE QUESTE INFO LE METTE SULLA STESSA RIGA!
    		sb.append(String.format("%-10d ", s.getMatricola()));
    		sb.append(String.format("%-50s ", s.getCognome()));
    		sb.append(String.format("%-40s ", s.getNome()));
    		sb.append(String.format("%-10s\n", s.getcDS()));
    	}
    	
    	this.txtRisultato.appendText(sb.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.txtRisultato.setStyle("-fx-font-family: monospace");
    }
    
    
}