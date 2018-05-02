package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercIdMap;
import it.polito.tdp.poweroutages.model.PowerOutages;
import it.polito.tdp.poweroutages.model.PowerOutagesIdMap;

public class PowerOutageDAO {

	public List<Nerc> getNercList(NercIdMap nercIdMap) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(nercIdMap.get(n));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutages> getPowerOutagesList(PowerOutagesIdMap powerOutagesIdMap, NercIdMap nercIdMap) {
			
		String sql = "select id,nerc_id,date_event_began,date_event_finished,customers_affected " + 
				"from poweroutages " + 
				"order by date_event_began ";
		List<PowerOutages> powerOutageslist = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutages p = new PowerOutages(res.getInt("id"), nercIdMap.get(res.getInt("nerc_id")),
													res.getTimestamp("date_event_began").toLocalDateTime(),
													res.getTimestamp("date_event_finished").toLocalDateTime(),
													res.getInt("customers_affected"));
				powerOutageslist.add(powerOutagesIdMap.get(p));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return powerOutageslist;
	}
	
	public List<PowerOutages> getPowerOutagesListFromNerc(PowerOutagesIdMap powerOutagesIdMap, NercIdMap nercIdMap, int nercId) {
		
		String sql = "select id,nerc_id,date_event_began,date_event_finished,customers_affected " + 
				"from poweroutages where nerc_id=? " + 
				"order by date_event_began ";
		List<PowerOutages> powerOutageslist = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nercId);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutages p = new PowerOutages(res.getInt("id"), nercIdMap.get(res.getInt("nerc_id")),
													res.getTimestamp("date_event_began").toLocalDateTime(),
													res.getTimestamp("date_event_finished").toLocalDateTime(),
													res.getInt("customers_affected"));
				powerOutageslist.add(powerOutagesIdMap.get(p));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return powerOutageslist;
	}

}
