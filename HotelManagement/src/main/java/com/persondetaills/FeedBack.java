package com.persondetaills;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The FeedBack class represents feedback provided by users.
 * @author Dinesh Kumar(expleo)
 * @since 20 feb 2024
 */
public class FeedBack {
    Connection conn;
    private int reservedId;
    private int rating;
    private String comments;

    /**
     * Constructs a FeedBack object with the provided database connection and feedback details.
     * @param conn The database connection
     * @param reservedId The reservation ID
     * @param rating The rating provided in the feedback
     * @param comments The comments provided in the feedback
     */
    public FeedBack(Connection conn, int reservedId, int rating, String comments) {
        this.conn = conn;
        this.reservedId = reservedId;
        this.rating = rating;
        this.comments = comments;
    }

    /**
     * Constructs a FeedBack object with the provided database connection.
     * @param conn The database connection
     */
    public FeedBack(Connection conn) {
        this.conn = conn;
    }

    /**
     * Returns the reservation ID associated with the feedback.
     * @return The reservation ID
     */
    public int getReservationId() {
        return reservedId;
    }

    /**
     * Sets the reservation ID associated with the feedback.
     * @param reservedId The reservation ID
     */
    public void setReservationId(int reservedId) {
        this.reservedId = reservedId;
    }

    /**
     * Returns the rating provided in the feedback.
     * @return The rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating provided in the feedback.
     * @param rating The rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Returns the comments provided in the feedback.
     * @return The comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments provided in the feedback.
     * @param comments The comments
     */
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
