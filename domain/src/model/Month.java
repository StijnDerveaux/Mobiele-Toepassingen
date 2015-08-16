package model;

public enum Month {
	JANUARY("Januari"), FEBRUARY("Februari"), MARCH("Maart"), APRIL("April"), MAY("Mei"), JUNE("Juni"), JULY("Juli"), AUGUST(
			"Augustus"), SEPTEMBER("September"), OCTOBER("Oktober"), NOVEMBER("November"), DECEMBER("December");
	private String nbrOfDays;

	private Month(String nbrOfDays) {
		this.nbrOfDays = nbrOfDays;
	}

	public String getMonth() {
		return nbrOfDays;
	}
	
}