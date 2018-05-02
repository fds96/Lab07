package it.polito.tdp.poweroutages.model;

import java.util.*;

public class PowerOutagesIdMap {

	private Map<Integer, PowerOutages> map;
	
	public PowerOutagesIdMap() {
		map = new HashMap<>();
	}
	
	public PowerOutages get(PowerOutages powerOutages) {
		PowerOutages old = map.get(powerOutages.getId());
		if (old == null) {
			// nella mappa non c'è!
			map.put(powerOutages.getId(), powerOutages);
			return powerOutages;
		}
		return old;
	}
	
	public void put(int id,PowerOutages powerOutages) {
		map.put(id, powerOutages);
	}
	
}
