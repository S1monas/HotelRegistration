package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GuestDao {
	
	Guest guest = new Guest();
	
	public void addGuest(Guest guest)
	{
		String sql = "INSERT INTO `hotelguests`"
				+ "(`name`, `surname`, `room`,`isbooked`)"
				+ " VALUES (?, ?, ?, ?)";
		try {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
		PreparedStatement add = myConn.prepareStatement(sql);
		add.setString(1,guest.getName());
		add.setString(2,guest.getSurname());
		add.setInt(3,guest.getRoom());
		add.setBoolean(4,guest.isIsbooked());		
		add.execute();
		add.close();
		}catch(Exception exc){
			
			exc.printStackTrace();		
		}
	}
	
	public void deleteRegistration(int id) {
	String sql = "delete FROM hotelguests WHERE id = ?";
	
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
			PreparedStatement del = myConn.prepareStatement(sql);
			del.setInt(1, id);
			del.executeUpdate();
			}catch(Exception exc){
				exc.printStackTrace();
			}
	}
	
	
	

	public void showGuests(ObservableList<Guest> guestInfo) {
		String sql = "SELECT * FROM hotelguests";

		try {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
		PreparedStatement show = myConn.prepareStatement(sql);
		ResultSet rs = show.executeQuery();
		while(rs.next()) {
			//System.out.println(rs.getInt("room"));	
			guestInfo.add(new Guest(
					rs.getString("name"),
					rs.getString("surname"),
					rs.getInt("room"),
					rs.getBoolean("isbooked"),
					rs.getInt("id")
					));
				}
		
		}catch(Exception exc){
			exc.printStackTrace();
		}	
	}
	
	public ObservableList<Guest> findEmptyRoom(boolean isbooked) {
		String sql = "SELECT * FROM hotelguests WHERE isbooked = '1'";
		ObservableList<Guest>guestInfo = FXCollections.observableArrayList();
		
		try {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
		PreparedStatement show = myConn.prepareStatement(sql);
		
		ResultSet rs = show.executeQuery();

		while(rs.next()) {
			guestInfo.add(new Guest(
					rs.getString("name"),
					rs.getString("surname"),
					rs.getInt("room"),
					rs.getBoolean("isbooked"),
					rs.getInt("id")
					));
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return guestInfo;
		
	}
		
	public void findBookedRooms(ObservableList<Guest> guestInfo) {
		String sql = "SELECT * FROM hotelguests WHERE isbooked = '1'";

		try {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
		PreparedStatement show = myConn.prepareStatement(sql);
		ResultSet rs = show.executeQuery();
		while(rs.next()) {
			guestInfo.add(new Guest(
					rs.getString("name"),
					rs.getString("surname"),
					rs.getInt("room"),
					rs.getBoolean("isbooked"),
					rs.getInt("id")
					));
		}
		
		}catch(Exception exc){
			exc.printStackTrace();
		}		
	}
	
	
	public void cancelRegistration(Guest guest)
	{
		try {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
		PreparedStatement conn = myConn.prepareStatement("UPDATE hotelguests SET isbooked = ? WHERE id =?");
		conn.setBoolean(1,guest.isIsbooked());
		conn.setInt(2, guest.getId());
		conn.executeUpdate();
		conn.close();
		}catch(Exception exc){
			exc.printStackTrace();		
		}
	}
	
	public ObservableList<Guest> showRoomHistory (int room) {
		String sql = "SELECT * FROM hotelguests WHERE room =" + room;
		ObservableList<Guest>guestInfo = FXCollections.observableArrayList();

		try {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
		PreparedStatement show = myConn.prepareStatement(sql);
		ResultSet rs = show.executeQuery();
		while(rs.next()) {	
			guestInfo.add(new Guest(
					rs.getString("name"),
					rs.getString("surname"),
					rs.getInt("room"),
					rs.getBoolean("isbooked"),
					rs.getInt("id")
					));
		}
		
		}catch(Exception exc){
			exc.printStackTrace();
		
		}
		
		return guestInfo;
		
	}
	
	public ObservableList<Guest> testforBookedRoom (int room){
		String sql = "SELECT * from hotelguests WHERE room ="+ room+ " AND isbooked =true";
		ObservableList<Guest>guestInfo = FXCollections.observableArrayList();

		try {
		Connection myConn = DriverManager.getConnection("jdbc:mysql://sql163.main-hosting.eu:3306/u613886194_db", "u613886194_sim", "just4projects");
		PreparedStatement show = myConn.prepareStatement(sql);
		ResultSet rs = show.executeQuery();
		while(rs.next()) {	
			guestInfo.add(new Guest(
					rs.getString("name"),
					rs.getString("surname"),
					rs.getInt("room"),
					rs.getBoolean("isbooked"),
					rs.getInt("id")
					));	
			return guestInfo;
		}
		
		}catch(Exception exc){
			exc.printStackTrace();
		
		}
		
	 return null;	
		
	}
}
	
