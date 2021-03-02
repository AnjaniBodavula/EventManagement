package com.cg.jdbce.model;
import java.io.Serializable;
import java.time.LocalDate;

public class Event implements Serializable, Comparable<Event> /* Comparator<Event> */ {

	private static final long serialVersionUID = 1L;
	private String serialNumber;
	private String title;
	private String location;
	private LocalDate startDate;
	private double cost;

	public Event() {

		/* This is a default constructor */

	}

	public Event(String serialNumber, String title, String location, LocalDate startDate, double cost) {
		super();
		this.serialNumber = serialNumber;
		this.title = title;
		this.location = location;
		this.startDate = startDate;
		this.cost = cost;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {

		StringBuilder string = new StringBuilder("Event Details:\n");
		string.append("Serial Number: ");
		string.append(serialNumber);
		string.append("\tEvent Title: ");
		string.append(title);
		string.append("\tStarting Date: ");
		string.append(startDate);
		string.append("\tLocation: ");
		string.append(location);
		string.append("\tCost: Rs. ");
		string.append(cost);

		return string.toString();

	}

	@Override
	public int compareTo(Event event) {

		String firstSerial = this.serialNumber;
		String secondSerial = event.serialNumber;
		return firstSerial.compareTo(secondSerial);
	}

	@Override
	public int hashCode() {

		int hashCode = 0;

		char[] chars = serialNumber.toCharArray();

		for (int i = 1; i <= chars.length; i++) {

			hashCode += ((int) chars[i - 1]) * i;
		}

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {

		boolean flag = false;

		if (obj instanceof Event) {

			Event event = (Event) obj;
			String firstSerial = this.serialNumber;
			String secondSerial = event.serialNumber;
			flag = firstSerial.equals(secondSerial);
		}

		return flag;
	}

	public void add(Event event) {
		// TODO Auto-generated method stub
		
	}

}