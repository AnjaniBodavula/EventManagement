package com.cg.jdbce.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.cg.jdbce.dao.EventImplementation;
import com.cg.jdbce.dao.IEventDAO;
import com.cg.jdbce.exception.EventManagementException;
import com.cg.jdbce.model.Event;

public class EventServiceImpl implements IEventService {

	private IEventDAO eventDao;

	public IEventDAO getDAO() {

		return eventDao;
	}

	public EventServiceImpl() throws EventManagementException {

		eventDao = new EventImplementation();

	}

	public boolean isValidSerialNumber(String serialNumber) {

		Pattern check = Pattern.compile("[A-Z]\\d{3}"); // Checks if the string starts with a capital letter and has
														
		Matcher match = check.matcher(serialNumber);
		return match.matches();

	}

	public boolean isValidTitle(String title) {

		Pattern check = Pattern.compile("[A-Z]\\w{3,19}"); 
															
		Matcher match = check.matcher(title);
		return match.matches();

	}

	public boolean isValidLocation(String location) {

		Pattern check = Pattern.compile("[A-Z]\\w{3,19}"); 
															
		Matcher match = check.matcher(location);
		return match.matches();

	}

	public boolean isValidStartDate(LocalDate startDate) {

		LocalDate today = LocalDate.now();
		return (today.isBefore(startDate) || today.isEqual(startDate));

	}

	public boolean isValidCost(Double cost) {

		return cost >= 5 && cost <= 5000; 

	}

	public boolean isValidEvent(Event event) throws EventManagementException {

		boolean flag = false;

		List<String> error = new ArrayList<>(); 
		if (!isValidSerialNumber(event.getSerialNumber())) {

			error.add("The serial number must start with a capital alphabet and have three numbers");

		}

		if (!isValidTitle(event.getTitle())) {

			error.add("The title must start with a capital letter and have atleast 4 to 20 elements");

		}

		if (!isValidStartDate(event.getStartDate())) {

			error.add("The start date must be in the format DD-MM-YYYY");

		}

		if (!isValidCost(event.getCost())) {

			error.add("The price of the event cannot exceed Rs. 5000 or be below Rs. 5");

		}

		if (error.isEmpty()) {

			flag = true;

		} else {

			throw new EventManagementException(error.toString()); 
		}

		return flag;

	}

	@Override
	public String add_event(Event event) throws EventManagementException {

		String serialNumber = null;
		if (event != null && isValidEvent(event)) {

			serialNumber = eventDao.add_event(event);
		}
		return serialNumber;
	}

	@Override
	public boolean remove_event(String serialNumber) throws EventManagementException {

		boolean flag = false;

		if (serialNumber != null && isValidSerialNumber(serialNumber)) {

			eventDao.remove_event(serialNumber);
			flag = true;

		} else {

			throw new EventManagementException(
					"The serial number must start with a capital alphabet and have three numbers");
		}
		return flag;

	}

	public List<Event> list_events_location() throws EventManagementException {

		return eventDao.list_events_location();

	}

	@Override
	public Set<Event> list_events() throws EventManagementException {

		return eventDao.list_events();

	}

	@Override
	public List<Event> list_events_date(LocalDate startDate) throws EventManagementException {

		return eventDao.list_events_date(startDate);

	}

	@Override
	public List<Event> list_location(String location) throws EventManagementException {

		return eventDao.list_location(location);

	}

	@Override
	public void persist() throws EventManagementException {

		eventDao.persist();
	}

}