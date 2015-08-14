package model;

public enum Month {
	JANUARY(31), FEBRUARY(29), MARCH(31), APRIL(30), MAY(31), JUNE(30), JULY(31), AUGUST(
			31), SEPTEMBER(30), OCTOBER(31), NOVEMBER(30), DECEMBER(31);
	private int nbrOfDays;

	private Month(int nbrOfDays) {
		this.nbrOfDays = nbrOfDays;
	}

	public int getNbrOfDays() {
		return nbrOfDays;
	}
	
}