package com.persondetaills;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedBack {
	Connection conn;
    private int reservedId;
    private int rating;
    private String comments;

    public FeedBack(Connection conn,int reservedId, int rating, String comments) {
    	this.conn=conn;
        this.reservedId = reservedId;
        this.rating = rating;
        this.comments = comments;
    }

	public FeedBack(Connection conn) {
		this.conn=conn;
	}

	public int getReservationId() {
        return reservedId;
    }

    public void setReservationId(int reservedId) {
        this.reservedId = reservedId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void ProvideFeedback() {
        try {
            String insertQuery = "INSERT INTO DINESH.feedback (reservation_id, rating, comments) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, reservedId);
            statement.setInt(2, rating);
            statement.setString(3, comments);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Your feedback is the compass that guides us towards excellence. Thank you for taking the time to share your thoughts with us.");
            } else {
                System.out.println("Failed to insert data into payment table.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void fetchFeedback(Connection conn) {
        String fetchValues = "SELECT * FROM DINESH.feedback";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(fetchValues);
            
            while (resultSet.next()) {
            	System.out.println("=".repeat(250));
                System.out.println("Feedback ID: " + resultSet.getInt(1));
                System.out.println("Rating: " + resultSet.getString(2));
                System.out.println("FeedBack Text:" +  resultSet.getString (3));
                System.out.println();
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
}
