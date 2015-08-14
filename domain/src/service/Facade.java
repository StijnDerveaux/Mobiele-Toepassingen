package service;

import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.Child;
import model.QrCode;
import model.User;
import db.Db;
import db.DbFactory;

public class Facade {
	private Db userDb;
	private DbFactory fac = new DbFactory();
	private static Facade instance = null;

	public Facade() {
		createDb("map");
		User ad = new Admin();
		Child u1 = new Child("Olive", "Nikki");
		Child u2 = new Child("Derveaux", "Stijn");
		Child u3 = new Child("De", "Held");
		Child u4 = new Child("Tom", "Boonen");
		Child u5 = new Child("Groen", "Veld");
		Child u6 = new Child("Paarse", "Held");
		Child u7 = new Child("Waarom", "Niet");
		Child u8 = new Child("Peeters", "Hans");
		
		
		int dag = 5;
		String maand = "JANUARY";
		int uren = 3;
		
		
		
		u1.addAanwezigheid2(dag, maand, uren,5);
		u1.addAanwezigheid2(7, "JANUARY", 1,6);
		u1.addAanwezigheid2(9, "JANUARY", 10,12);
		u1.addAanwezigheid2(11, "JANUARY", 8,10);
		u1.addAanwezigheid2(12, "JANUARY", 17,19);
		u1.addAanwezigheid2(14, "JANUARY", 17,19);
		u1.addAanwezigheid2(17, "JANUARY", 20,24);
		u1.addAanwezigheid2(18, "JANUARY", 17,21);
		u1.addAanwezigheid2(20, "JANUARY", 8,15);

		u1.addBedrag("JANUARY", 10, false);
		u1.addBedrag("February", 5, false);
		u1.addBedrag("MARCH", 20, true);
		u1.addBedrag("April", 105, true);
		u1.addBedrag("May", 100, false);
		u1.addBedrag("JUNE", 75, false);
		u1.addBedrag("JULY", 910, false);
		u1.addBedrag("AUGUST", 30, true);
		u1.addBedrag("SEPTEMBER", 51, false);
		u1.addBedrag("OCTOBER", 100, false);
		u1.addBedrag("NOVEMBER", 50, true);
		u1.addBedrag("DECEMBER", 70, true);
		
		u2.addBedrag("JANUARY", 10, true);
		addUser(u1);
		addUser(u2);
		addUser(u3);
		addUser(u4);
		addUser(u5);
		addUser(u6);
		addUser(u7);
		addUser(u8);
		
	}

	public User getUser(QrCode q) {
		/*
		 * String[] stringParts = us.split("/"); int number =
		 * Integer.parseInt(stringParts[0]); String naam = stringParts[1];
		 * String voornaam = stringParts[2]; QrCode q = new QrCode(number, naam,
		 * voornaam);
		 */
		return userDb.getUser(q);

	}

	public void addUser(User user) {
		/* User user=new Child(naam, voornaam); */
		userDb.addUser(user);
	}

	public void addAdmin() {
		User user = new Admin();
		userDb.addUser(user);
	}

	public void removeUser(User user) {
		userDb.removeUser(user);
	}

	public void updateUser(User user) {
		userDb.updateUser(user);
	}

	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userDb.getUsers();
	}

	public User getUser(int number) {
		return userDb.getUser(number);
	}

	public QrCode getQrCodeInfo(User user) {
		String[] stringParts = user.getQrCode().getCode().split("/");
		int number = Integer.parseInt(stringParts[0]);
		QrCode q = null;
		if (stringParts.length > 1) {
			String naam = stringParts[1];
			String voornaam = stringParts[2];
			q = new QrCode(number, naam, voornaam);
		} else {
			q = new QrCode(number);
		}
		return q;

	}

	public int getNumber(User user) {
		return user.getNumber();
	}

	public String getNaam(User u) {
		String[] stringParts = u.getQrCode().getCode().split("/");

		return stringParts[1];

	}

	public String getVoornaam(User u) {
		String[] stringParts = u.getQrCode().getCode().split("/");

		return stringParts[2];
	}

	public static Facade getInstance() {

		if (instance == null) {
			instance = new Facade();
		}

		return instance;
	}

	public void createDb(String type) {
		userDb = fac.Create(type);

	}

	public void closeConnection() {
		userDb.closeConnection();
	}
}
