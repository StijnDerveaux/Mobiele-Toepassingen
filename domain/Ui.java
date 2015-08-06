import service.Facade;
import model.Aanwezigheden;
import model.Admin;
import model.Child;
import model.QrCode;
import model.User;

public class Ui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Facade facade = new Facade();
		User ad = new Admin();
		Child u1 = new Child("d", "s");
		Child u2 = new Child("t", "p");
		Child u3 = new Child("u", "l");

		facade.addUser(ad);
		facade.addUser(u1);
		facade.addUser(u2);
		facade.addUser(u3);
		QrCode qA = new QrCode(-1);

		QrCode q = facade.getQrCodeInfo(u1);

		System.out.println(facade.getUsers());
		System.out.println(facade.getUser(qA));
		System.out.println(facade.getUser(q));
		System.out.println(facade.getNaam(u1));

		int dag = 5;
		String maand = "JANUARY";
		int uren = 3;
		System.out.println(u1.getAanwezigheden());
		u1.addAanwezigheid(dag, maand, uren);
		u1.addAanwezigheid(7, "JANUARY", 1);
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
