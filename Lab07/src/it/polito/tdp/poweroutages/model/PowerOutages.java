package it.polito.tdp.poweroutages.model;

import java.time.*;

public class PowerOutages {

	private int id;
	private Nerc nerc;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	private int coinvolti;
	
	public PowerOutages(int id, Nerc nerc, LocalDateTime dataInizio, LocalDateTime dataFine, int coinvolti) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.coinvolti = coinvolti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}

	public int getCoinvolti() {
		return coinvolti;
	}

	public void setCoinvolti(int coinvolti) {
		this.coinvolti = coinvolti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutages other = (PowerOutages) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String toString() {
		return this.dataInizio + " " + this.dataFine + " " + this.coinvolti;
	}
	
}
