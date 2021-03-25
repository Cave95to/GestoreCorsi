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
		
}