package com.roomreservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.search.Search; 
/**
 * The Room class represents a room in a hotel.
 * It provides methods to get and set room details, and to search for rooms by various criteria.
 *
 * @author Dinesh Karmegam(expleo)
 * @since 21 Feb 2024
 */

public class Room implements Search {
	private Connection conn;
	private int roomnumber;
	private String roomtype;
	private int rate;
	private String status;
    public Room(int roomnumber, String roomtype, int rate) {
		this.roomnumber = roomnumber;
		this.roomtype = roomtype;
		this.rate = rate;
		
	}
    public Room(Connection conn) {
    	this.conn=conn;
    }
    public Room(int roomnumber) {
        this.roomnumber=roomnumber;
    }
    
    public int getRoomnumber() {
		return roomnumber;
	}

	public void setRoomnumber(int roomnumber) {
		this.roomnumber = roomnumber;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    //@throws SQL Exception
    @Override
    public ResultSet searchRoomsByType(String roomType) throws SQLException {
        String searchSql = "SELECT * FROM DINESH.rooms WHERE room_type = ? And available = 'AVAILABLE' ";
        PreparedStatement searchStatement = conn.prepareStatement(searchSql);
        searchStatement.setString(1, roomType);
        return searchStatement.executeQuery();
    }
    //@throws SQL Exception
    @Override
    public ResultSet searchRoomsByPrice (double price) throws SQLException {
        String searchSql = "SELECT * FROM DINESH.rooms WHERE RATE = ? And available = 'AVAILABLE'";
        PreparedStatement searchStatement = conn.prepareStatement(searchSql);
        searchStatement.setDouble(1,price);
        return searchStatement.executeQuery();
    }
    //@throws SQL Exception
	
}

