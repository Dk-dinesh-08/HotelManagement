package com.search;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The Search interface defines methods for searching rooms by type, price, and room number.
 */
public interface Search {
    /**
     * Searches for rooms by the specified room type.
     *
     * @param roomType The type of room to search for.
     * @return A ResultSet containing the search results.
     * @throws SQLException If an SQL exception occurs.
     */
    ResultSet searchRoomsByType(String roomType) throws SQLException;

    /**
     * Searches for rooms by the specified room price.
     *
     * @param roomPrice The price of the room to search for.
     * @return A ResultSet containing the search results.
     * @throws SQLException If an SQL exception occurs.
     */
    ResultSet searchRoomsByPrice(double roomPrice) throws SQLException;

    /**
     * Searches for rooms by the specified room number.
     *
     * @param roomNumber The number of the room to search for.
     * @return A ResultSet containing the search results.
     * @throws SQLException If an SQL exception occurs.
     */
}
