import service.Facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import model.Aanwezigheden;
import model.Admin;
import model.Child;
import model.QrCode;
import model.User;

public class Ui {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		Facade facade = new Facade();
		User ad = new Admin();
		Child u1 = new Child("d", "s");
		Child u2 = new Child("t", "p");
		Child u3 = new Child("u", "l");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		Date date1 = simpleDateFormat.parse("14/08/2015 15:5:10");
	
		Date date2 = new Date(cal.getTimeInMillis());
		

		facade.addUser(ad);
		facade.addUser(u1);
		facade.addUser(u2);
		facade.addUser(u3);
		// QrCode qA = new QrCode(-1);

		QrCode q = facade.getQrCodeInfo(u1);

		//System.out.println(facade.getUsers());
		// System.out.println(facade.getUser(qA));
		//System.out.println(facade.getUser(q));
		//System.out.println(facade.getNaam(u1));

		int dag = 5;
		String maand = "JANUARY";
		int uren = 3;
		System.out.println(u1.getAanwezigheden());
		u1.addAanwezigheid(new Aanwezigheden(date2));
		
		Aanwezigheden b =u1.getAanwezigheid(new Aanwezigheden(date2));
		b.setVertrek(date1);
		u1.addAanwezigheid(b);
System.out.println(b.getAankomstUur());
		System.out.println(u1.getAanwezigheden());
		facade.updateUser(u1);
		u1 = (Child) facade.getUser(u1.getQrCode());
		System.out.println(u1.getAanwezigheden());

		u1.addBedrag("JANUARY", 10, false);
		u1.addBedrag("February", 5, false);
		System.out.println(u1.getBedragen());

		facade.updateUser(u1);
		u1 = (Child) facade.getUser(u1.getQrCode());
		System.out.println(u1.getBedragen());

		facade.removeUser(u2);
		System.out.println(facade.getUsers());
		/*
		 * System.out.println(facade.getUser(q));
		 * System.out.println(facade.getUser(qA));
		 */
	}
}
