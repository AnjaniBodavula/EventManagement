package com.cg.jdbce.dao;
import com.cg.jdbce.model.Event;
import com.cg.jdbce.util.ConnectionProvider;
import com.cg.jdbce.exception.EventManagementException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EventDAOJDBCImpl implements IEventDAO {

	ConnectionProvider conProvider;
	//Logger log;

	public EventDAOJDBCImpl() throws EventManagementException {
		
		try {
			conProvider = ConnectionProvider.getInstance();
		} catch (ClassNotFoundException | IOException exp) {
			//log.error(exp);
			throw new EventManagementException("Database is not reachable");
		}
	}

	@Override
	public String add(Event event, String serialnumber) throws EventManagementException {
		String ecode = null;
		if (event != null) {
			try (Connection con = conProvider.getConnection();
					PreparedStatement pInsert = con
							.prepareStatement(IQueryMapper.ADD_EVENT_QRY)) {

				pInsert.setString(1, event.getSerialNumber());
				pInsert.setString(2, event.getTitle());
				pInsert.setString(3,event.getLocation());
				pInsert.setDouble(4, event.getCost());
				pInsert.setDate(5, Date.valueOf(event.getStartDate()));

				int rowCount = pInsert.executeUpdate();

				if (rowCount == 1) {
					serialnumber = event.getSerialNumber();
				}
			} catch (SQLException exp) {
				
				throw new EventManagementException("Event is not inserted");
			}
		}
		return ecode;
	}

	@Override
	public boolean delete(String serialnumber) throws EventManagementException {
		boolean isDone = false;

		try (Connection con = conProvider.getConnection();
				PreparedStatement pDelete = con
						.prepareStatement(IQueryMapper.DEL_EVENT_QRY)) {

			pDelete.setString(1, serialnumber);

			int rowCount = pDelete.executeUpdate();

			if (rowCount == 1) {
				isDone = true;
			}
		} catch (SQLException exp) {
			//log.error(exp);
			throw new EventManagementException("Event is not inserted");
		}

		return isDone;
	}
	@Override
	public Event get(String serialnumber) throws EventManagementException {
		Event event=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con
						.prepareStatement(IQueryMapper.GET_EVENT_QRY)) {

			pSelect.setString(1, serialnumber);

			ResultSet rs = pSelect.executeQuery();
			
			if(rs.next()){
				event = new Event();
				event.setSerialNumber(rs.getString("serialnnumber"));
				event.setTitle(rs.getString("title"));
				event.setCost(rs.getDouble("cost"));
				event.setStartDate(rs.getDate("startdate").toLocalDate());
			}
			
		} catch (SQLException exp) {
			//log.error(exp);
			throw new EventManagementException("Book is not available");
		}		
		return event;
	}

	@Override
	public List<Event> getAll(List<Event> events) throws EventManagementException {
		List<Event> books=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con
						.prepareStatement(IQueryMapper.GET_ALL_EVENT_QRY)) {

			ResultSet rs = pSelect.executeQuery();
			
			books = new ArrayList<Event>();
			
			while(rs.next()){
				Event event = new Event();
				event.setSerialNumber(rs.getString("serialnumber"));
				event.setTitle(rs.getString("title"));
				event.setCost(rs.getDouble("cost"));
				event.setStartDate(rs.getDate("startdate").toLocalDate());
				event.add(event);
			}
			
		} catch (SQLException exp) {
			//log.error(exp);
			throw new EventManagementException("No Events are available.");
		}		
		return events;	
	}

	@Override
	public boolean update(Event event) throws EventManagementException {
		boolean isDone = false;
		if (event != null) {
			try (Connection con = conProvider.getConnection();
					PreparedStatement pUpdate = con
							.prepareStatement(IQueryMapper.MODIFY_EVENT_QRY)) {

				
				pUpdate.setString(1, event.getTitle());
				pUpdate.setDouble(2, event.getCost());
				pUpdate.setString(3,event.getLocation());
				pUpdate.setDate(4, Date.valueOf(event.getStartDate()));
				pUpdate.setString(5, event.getSerialNumber());
				

				int rowCount = pUpdate.executeUpdate();

				if (rowCount == 1) {
					isDone = true;
				}
			} catch (SQLException exp) {
				//log.error(exp);
				throw new EventManagementException("Event is not updated.");
			}
		}
		return isDone;
	}

	@Override
	public void persist() throws EventManagementException {
		/* no implementation required */
		
	}
}