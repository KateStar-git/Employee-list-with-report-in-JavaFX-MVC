package model;

import java.util.Objects;

public class Person implements Comparable<Person>{
  private String firstName;
  private String lastName;
  private String room;
  private int workStartHour;
  private int workEndHour;
  


public Person(String firstName, String lastName, String room, int workStartHour, int workEndHour) {
	this.firstName=firstName;
	this.lastName=lastName;
	this.room=room;
	this.workStartHour = workStartHour;
	this.workEndHour = workEndHour;
}


public int getWorkStartHour() {
	return workStartHour;
}

public void setWorkStartHour(short workStartHour) {
	this.workStartHour = workStartHour;
}

public int getWorkEndHour() {
	return workEndHour;
}

public void setWorkEndHour(short workEndHour) {
	this.workEndHour = workEndHour;
}

  public String getFirstName() {
	  return firstName;
  }
  
  public String getLastName() {
	  return lastName;
  }
  
  public String getRoom() {
	  return room;
  }
  
  public void setFirstName(String firstName) {
	  this.firstName=firstName;
  }
  
  public void setLastName(String lastName) {
	  this.lastName=lastName;
  }
  
  public void setRoom(String room) {
	  this.room=room;
  }


@Override
public int hashCode() {
	return Objects.hash(firstName, lastName);
}


@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (!(obj instanceof Person))
		return false;
	Person other = (Person) obj;
	return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
}


@Override
public String toString() {
	return firstName + " " + lastName + " " + room + " "+ workStartHour + " " + workEndHour;
}


@Override
public int compareTo(Person p) {
	// TODO Auto-generated method stub
	return (this.workEndHour-this.workStartHour) - (p.workEndHour-p.workStartHour);
}
  

  
}
