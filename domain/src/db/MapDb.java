package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.QrCode;
import model.User;

public class MapDb implements Db {

	private final Map<Integer, User> users = new HashMap<>();
	public static int identifer;

	@Override
	public User getUser(QrCode qr) {
		// TODO Auto-generated method stub
		String[] stringParts = qr.getCode().split("/");

		int nummer = Integer.parseInt(stringParts[0]);
		User us = users.get(nummer);

		return us;
	}
	


	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		if (user == null) {
		}
		if (user.getNumber() == -1 ) {
			users.put(-1, user);
		} else {
			//identifer++;
			//user.setNumber(identifer);
			users.put(user.getNumber(), user);
			
		}

	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return new ArrayList<>(users.values());
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		users.put(user.getNumber(), user);

	}

	@Override
	public void removeUser(User user) {
		users.remove(user.getNumber());

	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public User getUser(int number) {
		User u=null;
		if(number == -1 || number >0){
			
			u=users.get(number);
		}
	return u;
	}

}
