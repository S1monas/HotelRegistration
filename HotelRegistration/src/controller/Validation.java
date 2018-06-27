package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	private static final String VALID_ID_REGEX ="[1-9][0-9]*$";
	private static final String VALID_ROOM_REGEX ="^([1-5])$";
	private static final String VALID_GUESTNAME_REGEX ="^[a-zA-Z]{1,20}$";
	private static final String VALID_GUESTSURNAME_REGEX ="^[a-zA-Z]{1,20}$";
	
	public static boolean isValidRoom(String room){
		Pattern IDPattern = Pattern.compile(VALID_ROOM_REGEX);
		Matcher IDMatcher = IDPattern.matcher(room);
		return IDMatcher.find();
	}
	public static boolean isValidID(String ID){
		Pattern IDPattern = Pattern.compile(VALID_ID_REGEX);
		Matcher IDMatcher = IDPattern.matcher(ID);
		return IDMatcher.find();
	}
	public static boolean isValidNameToRegister(String name){
		Pattern guestNamePattern = Pattern.compile(VALID_GUESTNAME_REGEX);
		Matcher guestNameMAtcher = guestNamePattern.matcher(name);
		return guestNameMAtcher.find();
	}
	public static boolean isValidSurnameToRegister(String surname){
		Pattern guestSurnamePattern = Pattern.compile(VALID_GUESTSURNAME_REGEX);
		Matcher guestSurnameMAtcher = guestSurnamePattern.matcher(surname);
		return guestSurnameMAtcher.find();
	}

}
