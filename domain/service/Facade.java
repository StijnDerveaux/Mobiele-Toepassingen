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
		int dag = 5;
		String maand = "JANUARY";
		int uren = 3;
		u1.addAanwezigheid(dag, maand, uren);
		u1.addAanwezigheid(7, "JANUARY", 1);

		u1.addBedrag("JANUARY", 10, false);
		u1.addBedrag("February", 5, false);
		
		addUser(u1);
		addUser(u2);
		addUser(u3);
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
