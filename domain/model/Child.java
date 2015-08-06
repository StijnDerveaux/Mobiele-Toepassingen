package model;

import java.util.ArrayList;
import java.util.List;

public class Child implements User {
	private String naam;
	private String voornaam;
	private int number;
	private QrCode qrcode;
	private List<Aanwezigheden> aanwezigheden = new ArrayList<>();
	private List<Bedragen> bedragen = new ArrayList<>();

	public Child(String naam, String voornaam) {
		setNaam(naam);
		
		setVoornaam(voornaam);
		//setQrCode(number, naam, voornaam);
	}

	@Override
	public QrCode getQrCode() {
		// TODO Auto-generated method stub
		return qrcode;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		if (naam != null) {
			this.naam = naam;
		}
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		if (voornaam != null) {
			this.voornaam = voornaam;
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		if (number > 0) {
			this.number = number;
			setQrCode(number, naam, voornaam);
		}
	}

	public void setQrCode(int number, String naam, String voornaam) {
		if (number > 0 && naam != null && voornaam != null) {
			qrcode = new QrCode(number, naam, voornaam);
		}
	}

	@Override
	public boolean equals(Object object) {
		boolean gelijk = false;
		if (object != null && object instanceof Child) {
			Child aan = (Child) (object);
			if (this.getNumber()==aan.getNumber()) {
				gelijk = true;
			}
		}
		return gelijk;
	}

	public void addAanwezigheid(int dag, String maand, int uren) {
		if (dag > 0 && dag < 32 && maand != null && uren >= 0) {
			Aanwezigheden aan = new Aanwezigheden(dag, maand, uren);
			aanwezigheden.add(aan);
		}
	}

	public void addBedrag(String maand, int bedrag, boolean betaald) {
		if (maand != null && bedrag > 0) {
			Bedragen bed = new Bedragen(maand, bedrag, betaald);
			bedragen.add(bed);

		}

	}

	public List<Aanwezigheden> getAanwezigheden() {
		return aanwezigheden;
	}

	public List<Bedragen> getBedragen() {
		return bedragen;
	}

}
