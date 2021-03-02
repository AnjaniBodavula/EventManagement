package com.cg.jdbce.dao;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.cg.jdbce.exception.EventManagementException;
import com.cg.jdbce.model.Event;

public interface IEventDAO {
	
	String add_event(Event event) throws EventManagementException;
	boolean remove_event(String serialNumber) throws EventManagementException;
	Set <Event> list_events() throws EventManagementException;
	List <Event> list_events_location() throws EventManagementException;
	List <Event> list_location(String location) throws EventManagementException;
	List <Event> list_events_date(LocalDate startDate) throws EventManagementException;
	void persist()throws EventManagementException;
	String add(Event event) throws EventManagementException;
	boolean delete(String serialnumber) throws EventManagementException;
	String add(Event event, String serialnumber) throws EventManagementException;
	Event get(String serialnumber) throws EventManagementException;
	List<Event> getAll() throws EventManagementException;
	boolean update(Event event) throws EventManagementException;
	List<Event> getAll(List<Event> events) throws EventManagementException;

}
