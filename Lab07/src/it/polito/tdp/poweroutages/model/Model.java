package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.*;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	private List<PowerOutages> powerOutagesList;
	private List<Nerc> nercList;
	private NercIdMap nercMap;
	private PowerOutagesIdMap powerOutagesMap;
	private int anniMax;
	private int oreMax;
	private List<PowerOutages> soluzione;
	private long numeroTotaleCoinvolti;
	private long oreSoluzione;
	
	public Model() {
		podao = new PowerOutageDAO();
		
		nercMap = new NercIdMap();
		powerOutagesMap = new PowerOutagesIdMap();
		
		nercList = podao.getNercList(nercMap);
		powerOutagesList = podao.getPowerOutagesList(powerOutagesMap, nercMap);
		
		soluzione = new ArrayList<>();
	}
	
	public Nerc getNercByName(String name) {
		for(Nerc n : this.getNercList()) {
			if(n.getValue().equals(name)) 
				return n;
		}
		return null;
	}
	
	
	public List<PowerOutages> getSoluzione() {
		return soluzione;
	}

	public long getNumeroTotaleCoinvolti() {
		return numeroTotaleCoinvolti;
	}

	public List<Nerc> getNercList() {
		return this.nercList;
	}
	
	public List<PowerOutages> getPowerOutagesList(){
		return this.powerOutagesList;
	}
	
	public List<PowerOutages> getPowerOutagesListFromNerc(int nercId){
		return podao.getPowerOutagesListFromNerc(powerOutagesMap, nercMap, nercId);
	}
	
	public void worstCaseAnalysis(int id, int anni, int ore) {
		List<PowerOutages> powerOutages = this.getPowerOutagesListFromNerc(id);
		anniMax=anni;
		oreMax=ore;
		numeroTotaleCoinvolti=0;
		oreSoluzione = 0;
		soluzione.clear();
		this.recursive(new ArrayList<PowerOutages>(), powerOutages, 0);
		return;
	}
	
	private void recursive(List<PowerOutages> parziale, List<PowerOutages> powerOutages, int livello) {
		
		if(!differenzaAnniNonSuperata(parziale) ) {
			return;
		}
		
		if(!sommaTempiNonSuperata(parziale)) {
			return;
		}
		
		int parzialeCoinvolti=0;
		long durataTotale=0;
		
		for(PowerOutages p : parziale) {
			parzialeCoinvolti+=p.getCoinvolti();
			durataTotale += (Duration.between(p.getDataInizio(), p.getDataFine())).toHours();
		}

		if(parzialeCoinvolti>numeroTotaleCoinvolti) {
			soluzione = new ArrayList<>(parziale);
			numeroTotaleCoinvolti=parzialeCoinvolti;
			oreSoluzione = durataTotale;
		}
		
		for(PowerOutages p : powerOutages) {
			boolean condizione = false;
			if(parziale.size()>0) 
				condizione = Duration.between(p.getDataInizio(), parziale.get(parziale.size()-1).getDataInizio()).isNegative() ||
				Duration.between(p.getDataInizio(), parziale.get(parziale.size()-1).getDataInizio()).isZero();
			//Ritorna true se la differenza tra l'anno di p e l'ultimo anno di parziale è negativa o zero
			if(!condizione) {
				parziale.add(p);
				this.recursive(parziale, powerOutages, livello+1);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	public long getOreSoluzione() {
		return oreSoluzione;
	}

	private boolean sommaTempiNonSuperata(List<PowerOutages> parziale) {
		long durataTotale = 0;
		if(parziale.size()==0)
			return true;
		if(parziale.size()==1) {
			PowerOutages p = parziale.get(0);
			durataTotale+=Duration.between(p.getDataInizio(), p.getDataFine()).toHours();
			if(durataTotale>oreMax)
				return false;
		}
		for(PowerOutages p : parziale) {
			durataTotale+=Duration.between(p.getDataInizio(), p.getDataFine()).toHours();
		}
		if(durataTotale>oreMax)
			return false;
		return true;
	}

	private boolean differenzaAnniNonSuperata(List<PowerOutages> parziale) {
		//Ritorna false se la differenza tra la data più vecchia e quella più nuova è maggiore del massimo
		if(parziale.size()==0 || parziale.size()==1)
			return true;
		int differenza = parziale.get(0).getDataInizio().getYear() - parziale.get(parziale.size()-1).getDataFine().getYear();
		if(differenza>this.anniMax)
			return false;
		return true;
	}

}
