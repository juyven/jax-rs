package org.demo.rest.bean;

import java.util.Date;

public class Evenement {

	private String nom;

	private String Adresse;

	private Date dateDebut;

	private Date dateFin;

	private boolean publish = false;

	public String getNom() {
		return nom;
	}

	public String getAdresse() {
		return Adresse;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public Boolean getPublish() {
		return publish;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setAdresse(String adresse) {
		Adresse = adresse;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}

	@Override
	public String toString() {
		return "Evenement [nom=" + nom + ", Adresse=" + Adresse
				+ ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + "]";
	}
}
