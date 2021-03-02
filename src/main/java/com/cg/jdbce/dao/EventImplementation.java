package com.cg.jdbce.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.cg.jdbce.exception.EventManagementException;
import com.cg.jdbce.model.Event;
import com.cg.jdbce.service.LocationComparator;
import com.cg.jdbce.service.DateComparator;

public class EventImplementation implements IEventDAO{
	
	public static final String DATA_STORE_FILE_NAME = "eventlist.dat";
	private Map <String,Event> events;
	
	
	public EventImplementation() throws EventManagementException {
		
		File file = new File(DATA_STORE_FILE_NAME);

		if (!file.exists()) {
			
			events = new TreeMap <> ();
			
		} else {
			
			try (ObjectInputStream fin = new ObjectInputStream(new FileInputStream(DATA_STORE_FILE_NAME))) {

				Object obj = fin.readObject();

				if (obj instanceof Map) {
					
					events = (Map <String, Event>) obj;
					
				} else {
					
					throw new EventManagementException("Not a valid DataStore");
				}
			} catch (IOException | ClassNotFoundException exp) {
				
				throw new EventManagementException("Loading Data Failed");
			}
		}
		
	}
	
	@Override
	public String add_event(Event event) throws EventManagementException {
		
		String serialNumber = null; 
		if (event != null) {
			
			serialNumber = event.getSerialNumber();
			events.put(serialNumber, event);	
		}
		
		return serialNumber;
	}
	
	@Override
	public boolean remove_event(String serialNumber) throws EventManagementException {
		
		boolean flag = false;
		if(serialNumber != null) {
			
			events.remove(serialNumber);
			flag = true;
			
		}
		
		return flag;
		
	}
	
	@Override
	public List <Event> list_events_location() throws EventManagementException {
		
		ArrayList <Event> list = new ArrayList <Event> (events.values());
		Collections.sort(list, new LocationComparator());
		return list;
		
	}
	
	@Override
	public Set <Event> list_events() throws EventManagementException {
		
		ArrayList <Event> list = new ArrayList <Event> (events.values());
		Set <Event> set = new TreeSet <> (new DateComparator());
		set.addAll(list);
		return set;
		
	}
	
	@Override
	public List <Event> list_events_date(LocalDate startDate) throws EventManagementException {
		
		ArrayList <Event> list = new ArrayList <Event> (events.values());
		ArrayList <Event> dateList = new ArrayList <> ();
		
		for(Event e : list) {
			
			if(e.getStartDate().equals(startDate)) {
				
				dateList.add(e);
				
			}
			
		}
		return dateList;
		
	}
	
	@Override
	public List <Event> list_location(String location) throws EventManagementException {
		
		ArrayList <Event> list = new ArrayList <Event> (events.values());
		ArrayList <Event> locationList = new ArrayList <> ();
		
		for(Event e : list) {
			
			if(e.getLocation().equals(location)) {
				
				locationList.add(e);
				
			}
			
		}
		return locationList;
		
	}
	
	@Override
	public void persist() throws EventManagementException {
		
		try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(DATA_STORE_FILE_NAME))) {
			
			fout.writeObject(events);
			
		} catch (IOException exp) {
			
			throw new EventManagementException("Saving Data Failed" + exp.getMessage());
		}
	}

}