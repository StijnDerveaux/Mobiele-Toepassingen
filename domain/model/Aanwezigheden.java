package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Aanwezigheden {
	private int dag;
	private Month maand;
	private int uren;
	private Date vertrek;
	private Date aankomst;
	private int aankomstUur;
	private int vertrekUur;



	public Aanwezigheden(int dag, String maand, int aankomstUur,int vertrekUur) {
		setDag(dag);
		setMaand(maand);
		setAankomstUur(aankomstUur);
		setVertrekUur(vertrekUur);
		// Calendar.getInstance().getTime()
	}
	public Aanwezigheden(Date aankomst) {
		setAankomst(aankomst);
		uren = 0;
		
	}
	
	public Aanwezigheden(Date aankomst,Date vertrek) {
		setAankomst(aankomst);
		setVertrek(vertrek);
	
		
	}
	public int getAankomstUur() {
		return aankomstUur;
	}

	public void setAankomstUur(int aankomstUur) {
		this.aankomstUur = aankomstUur;
	}

	public int getVertrekUur() {
		return vertrekUur;
	}

	public void setVertrekUur(int vertrekUur) {
		this.vertrekUur = vertrekUur;
		setAantalUren();
	}
	



	public Date getVertrek() {
		return vertrek;
	}

	public void setVertrek(Date vertek) {
		this.vertrek = vertek;
		urenZetten("ver");
		setAantalUren();
	}

	public Date getAankomst() {
		return aankomst;
	}

	public void setAankomst(Date aankomst) {
		this.aankomst = aankomst;
		getMonth(aankomst);
		urenZetten("aan");
		
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
		for (Month m : Month.values()) {
			if ((m.toString()).equalsIgnoreCase(maand))
				this.maand = m;
		}
	}

	public void setMaand(Month maand) {

		this.maand = maand;

	}

	public int getUren() {
		return uren;
	}

	public void setUren(int uren) {
		this.uren = uren;
	}

	public void addUren(int uren) {
		this.uren = getUren() + uren;
	}

	@Override
	public boolean equals(Object object) {
		boolean gelijk = false;
		if (object != null && object instanceof Aanwezigheden) {
			Aanwezigheden aan = (Aanwezigheden) (object);
			if (this.getMaand().equals(aan.getMaand()) && this.getDag() == aan.getDag()) {
				gelijk = true;
			}
		}
		return gelijk;
	}

	public String toString() {
		String uitvoer = "";
		uitvoer = dag + " /" + maand + " / " + uren;
		return uitvoer;

	}

	private void getMonth(Date d) {

		DateFormat inputDF = new SimpleDateFormat("MM/dd/yy");

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		// month is zero based
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		// int year = cal.get(Calendar.YEAR);

		setMaand(wichMonth(month));
		setDag(day);

	}

	private Month wichMonth(int maand) {
		Month month = null;
		switch (maand) {
		case 1:
			month = Month.JANUARY;

		case 2:
			month = Month.FEBRUARY;
		case 3:
			month = Month.MARCH;
		case 4:
			month = Month.APRIL;
		case 5:
			month = Month.MAY;

		case 6:
			month = Month.JUNE;
		case 7:
			month = Month.JULY;
		case 8:
			month = Month.AUGUST;
		case 9:
			month = Month.SEPTEMBER;
		case 10:
			month = Month.OCTOBER;
		case 11:
			month = Month.NOVEMBER;
		case 12:
			month = Month.DECEMBER;
		}

		return month;
	}
	private void urenZetten(String voorwaarde){
		long different ;
		if(voorwaarde.equals("aan")){
			 different =getAankomst().getTime();
		}
		else {
			different = getVertrek().getTime();
		}
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;
		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;
		if(voorwaarde.equals("aan")){
		setAankomstUur(getAankomst().getHours());
		}
		else{
			setVertrekUur(getVertrek().getHours());
			
		}
	
		
		
	}

	private void setAantalUren() {

	
		setUren(getVertrekUur()-getAankomstUur());
	

	}
	public boolean isAangekomen(){
		if(getAankomst()!=null){
			return true;
		}
		else return false;
	}
	public boolean isVertrokken(){
		if(getVertrek()!=null){
			return true;
		}
		else return false;
	}

}
