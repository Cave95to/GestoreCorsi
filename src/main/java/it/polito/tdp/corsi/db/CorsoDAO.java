package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	//ci restituisce un elenco di corsi, è quindi necessario creare una classe Corso DENTRO IL MODELLO
	public List<Corso> getCorsiByPeriodo(Integer periodo) {
		
		String sql = "SELECT * "
				+ "FROM corso "
				+ "WHERE pd = ?";
		
		List<Corso> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				Corso c = new Corso (rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				result.add(c);
			}
			
			conn.close();
			return result;
			
		}catch(SQLException e) {
			throw new RuntimeException("Database error in getCorsiByPeriodo", e);
		}
	}
	
	public Map<Corso,Integer> getTotIscrittiCorsiByPeriodo(Integer periodo){
		
		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot "
					+ "FROM corso c, iscrizione i "
					+ "WHERE c.codins=i.codins AND c.pd= ? "
					+ "GROUP BY c.codins, c.nome, c.crediti, c.pd";
			
		Map<Corso, Integer> result = new HashMap<>();
			
		try {
				
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
					
				Corso c = new Corso (rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
							rs.getInt("pd"));
				
				Integer n = rs.getInt("tot");
				
				result.put(c,n);
			}
				
			conn.close();
			return result;
				
		}catch(SQLException e) {
			throw new RuntimeException("Database error in getTotIscrittiCorsiByPeriodo", e);
		}
	}
		
	// avremmo potuto avere come parametro String codice, più semplice
	public List<Studente> getStudentiByCorso(Corso corso) {
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM iscrizione i, studente s "
				+ "WHERE s.matricola=i.matricola AND i.codins = ?";
		
		List<Studente> result = new ArrayList<>();
		
		Connection conn;
		
		try {
			
			conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				// ovviamente conta ordine del costruttore e non della query! quindi prima nome poi cognome
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"),
						rs.getString("CDS"));
				
				result.add(s);
				
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Database error in getStudentiByCorso", e);
		}
	}

	public boolean esisteCorso(Corso corso) {
		/* INVECE DI CERCARE TUTTI I CODICI in una lista E POI VEDERE SE ESISTE IL CORSO, LO POSSO FARE DIRETTAMENTE SENZA
		 * SALVARE NULLA con la query
		String sql = "SELECT codins "
				+ "FROM corso";
		
		List<String> result = new ArrayList<>();
		*/
		
		String sql = "SELECT * "
				+ "FROM corso "
				+ "WHERE codins = ? ";
		
		Connection conn;
		
		try {
			
			conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return true;
			}
			
			rs.close();
			st.close();
			conn.close();
			
			//if (result.contains(corso.getCodins()))
			//	return true;
			
			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Database error in esisteCorso", e);
		}
	}

	public Map<String, Integer> getDivisioneCDS(String codice) {
		
		String sql = "SELECT s.CDS, COUNT(*) AS tot "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola AND i.codins= ? AND s.cds<>'' "
				+ "GROUP BY s.CDS";
		
		Map<String, Integer> divisione = new HashMap<>();
		
		Connection conn;
		
		try {
			
			conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codice);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				divisione.put(rs.getString("cds"), rs.getInt("tot"));
				
			}
			rs.close();
			st.close();
			conn.close();
			return divisione;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Database error in getDivisioneCDS", e);
		}
	}
	
}