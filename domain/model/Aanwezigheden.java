package model;

public class Aanwezigheden {
	private int dag;
	private Month maand;
	private int uren;
	
	public Aanwezigheden(int dag,String maand,int uren){
		setDag(dag);
		setMaand(maand);
		setUren(uren);
		
	}

	public int getDag() {
		return dag;
	}

	public void setDag(int dag) {
		this.dag = dag;
	}

	public Month getMaand() {
		return maand;
	}

	public void setMaand(String maand) {
		for(Month m : Month.values()){
			if((m.toString()).equalsIgnoreCase(maand))
		this.maand = m;
		}
	}

	public int getUren() {
		return uren;
	}

	public void setUren(int uren) {
		this.uren = uren;
	}
	public void addUren(int uren){
		this.uren = getUren()+uren;
	}
	 @Override
	    public boolean equals(Object object) {
	        boolean gelijk = false;
	        if (object != null && object instanceof Aanwezigheden) {
	        	Aanwezigheden aan = (Aanwezigheden) (object);
	            if (this.getMaand().equals(aan.getMaand()) && this.getDag()== aan.getDag())  {
	                gelijk = true;
	            }
	        }
	        return gelijk;
	    }
	public String toString(){
		String uitvoer="";
		uitvoer = dag + " /" + maand + " / " + uren;
		return uitvoer;
		
	}
	
	
	
	
	

}
