package db;

import java.util.List;

import model.QrCode;
import model.User;

public interface Db {
	public User getUser(QrCode qr);

	public void addUser(User user);

	public List<User> getUsers();

	public void removeUser(User user);
	public void updateUser(User user);
	public void closeConnection();
public User getUser(int number);
}
