package model;

public class RoomNumbers {
	
	private RoomNumbers() {
	}
	
	public static final int MIN_ROOM_NUMBER=1;
	public static final int MAX_ROOM_NUMBER=20;
	
		 private static boolean verifyIfRoomNumberIsInRange (int roomNo) {
		 if (roomNo >= MIN_ROOM_NUMBER && roomNo<=MAX_ROOM_NUMBER) {
			 return true;
		 } else {
			 return false;
		 }
	 }
	 public static boolean verifyRoomNumber (String RoomNoStr) {
		 if (RoomNoStr.matches("\\d*")) {//d - jakakolwiek cyfra [0-9]
			 return(verifyIfRoomNumberIsInRange(Integer.valueOf(RoomNoStr)));
		 }
		else {
			return false;
		}	 
	 } 
}
