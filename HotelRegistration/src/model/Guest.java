package model;

public class Guest {
	int id;
	String name;
	String surname;
	int room;
	boolean isbooked;
	
	//Used only to cancel registration by the id in DB
	public Guest(String name, String surname, int room,boolean isbooked, int id) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.room = room;
		this.isbooked = isbooked;
	}
	
	public Guest(String name, String surname, int room, boolean isbooked) {
		super();
		this.name = name;
		this.surname = surname;
		this.room = room;
		this.isbooked = isbooked;
	}
	
	public Guest() {
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getId() {
		return id;
	}

	public boolean isIsbooked() {
		return isbooked;
	}

	public void setIsbooked(boolean isbooked) {
		this.isbooked = isbooked;
	}
	
}
