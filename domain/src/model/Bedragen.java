package model;

public class Bedragen {
	private Month maand;
	private int bedrag;
	private boolean betaald;

	public Bedragen(String maand, int bedrag, boolean betaald) {
		setMaand(maand);
		setBedrag(bedrag);
		setBetaald(betaald);
	}


	public Month getMaand() {
		return maand;
	}

	public void setMaand(String maand) {
		for (Month m : Month.values()) {
			if ((m.toString()).equalsIgnoreCase(maand))
				this.maand = m;
		}
	}

	public int getBedrag() {
		return bedrag;
	}

	public void setBedrag(int bedrag) {
		this.bedrag = bedrag;
	}

	public boolean isBetaald() {
		return betaald;
	}

	public void setBetaald(boolean betaald) {
		this.betaald = betaald;
	}

	public void zetBetaald() {
		setBetaald(true);
	}

	public void zetOnBetaald() {
		setBetaald(false);
	}

	
	 @Override
	    public boolean equals(Object object) {
	        boolean gelijk = false;
	        if (object != null && object instanceof Bedragen) {
	        	Bedragen aan = (Bedragen) (object);
	            if (this.getMaand().equals(aan.getMaand()))  {
	                gelijk = true;
	            }
	        }
	        return gelijk;
	    }
	 
		public String toString(){
			String uitvoer="";
			uitvoer =   maand + " / " + bedrag + " / " + betaald;
			return uitvoer;
			
		}
}
