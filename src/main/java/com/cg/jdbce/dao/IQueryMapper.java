package com.cg.jdbce.dao;

public interface IQueryMapper {

	public static final String ADD_EVENT_QRY = 
			"INSERT INTO events(ecode,  title, price, edate) VALUES(?,?,?,?)";
	public static final String MODIFY_EVENT_QRY = 
			"UPDATE events SET title=?,price=?,edate=? WHERE ecode=?";
	public static final String DEL_EVENT_QRY = 
			"DELETE FROM evnets WHERE Ecode=?";
	public static final String GET_ALL_EVENT_QRY = 
			"SELECT * FROM events";
	public static final String GET_EVENT_QRY = 
			"SELECT * FROM events WHERE ecode=?";
}