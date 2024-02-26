package com.persondetaills;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.enumurations.BookingStatus;
import com.enumurations.RoomType;
import com.roomreservation.Room;
/**
 * Represents an administrative user in the hotel management system.
 * 
 * This class extends the functionality of the Person class to include administrative privileges
 * such as managing room reservations, handling customer inquiries, and accessing sensitive system features.
 * 
 * @author Dinesh Karmegam(expleo)
 * @since  21 Feb 2024
 */
public class Admin extends Person {
    private Connection conn;
    private int adminId;
 


    public Admin(Connection conn) {
        super(conn);
        this.conn=conn;
    }
    
    public Admin(String firstName, String lastName,int age, String gender, String email, String dob, String phone,
                 String address, String zipcode, Account account) {
        super(firstName, lastName,age, gender, email, dob, phone, address, zipcode,account);
    }

    private int adminID;
    private String adminName;


    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public void addRoom(Room room) {
        try {
        	
            String sql = "INSERT INTO DINESH.rooms VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, room.getRoomnumber());
            statement.setString(2, room.getRoomtype());
            statement.setDouble(3, room.getRate());
            statement.setString(4,BookingStatus.AVAILABLE.toString());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Room added successfully.");
            } else {
                System.out.println("Failed to add room.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoom(BufferedReader reader) {
        try {
        	
            boolean updating = true;
            while (updating) {
            	System.out.println("\t===============================");
            	System.out.println("\t| 1. Update room type         |");
            	System.out.println("\t| 2. Update room price        |");
            	System.out.println("\t|  3. Go back                 |");
            	System.out.println("\t===============================");

            	System.out.print("Enter your choice: ");

                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    int roomNumber = Integer.parseInt(reader.readLine());
                    System.out.println("\t===============================");
                    System.out.println("\t|  Enter new room type:       |");
                    System.out.println("\t===============================");
                    System.out.println("\t|   1. Single                 |");
                    System.out.println("\t|   2. Double                 |");
                    System.out.println("\t|   3. Suite                  |");
                    System.out.println("\t|   4. Go back                |");
                    System.out.println("\t===============================");

                    int number = Integer.parseInt(reader.readLine());
                    String newRoomType;
                    switch (number) {
                        case 1:
                            System.out.println("1. AC\n2. Non AC");
                            int acChoice = Integer.parseInt(reader.readLine());
                            newRoomType = (acChoice == 1) ? RoomType.SINGLE_AC.toString() :  RoomType.SINGLE_NON_AC.toString() ;
                            break;
                        case 2:
                            System.out.println("1. AC\n2. Non AC");
                            acChoice = Integer.parseInt(reader.readLine());
                            newRoomType = (acChoice == 1) ? RoomType.DOUBLE_AC.toString() : RoomType.DOUBLE_NON_AC.toString();
                            break;
                        case 3:
                            System.out.println("1. AC\n2. Non AC");
                            acChoice = Integer.parseInt(reader.readLine());
                            newRoomType = (acChoice == 1) ? RoomType.SUITE_AC.toString() : RoomType.SUITE_NON_AC.toString();
                            break;
                        case 4:
                            System.out.println("Going back to the previous menu...");
                            return;
                        default:
                            System.out.println("Invalid choice.");
                            return;
                    }
                    updateRoomType(roomNumber, newRoomType);
                    break;

                    case 2:
                        System.out.print("Enter room number: ");
                        int roomNum = Integer.parseInt(reader.readLine());
                        System.out.print("Enter new room price: ");
                        double newPrice = Double.parseDouble(reader.readLine());
                        updateRoomPrice(roomNum, newPrice);
                        break;
                    case 3:
                        System.out.println("Exiting room update menu.");
                        updating = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException | NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRoomType(int roomNumber, String newRoomType) throws SQLException {
        String sql = "UPDATE DINESH.rooms SET room_type = ? WHERE room_number = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, newRoomType);
        statement.setInt(2, roomNumber);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Room type updated successfully.");
        } else {
            System.out.println("Failed to update room type.");
        }
    }

    private void updateRoomPrice(int roomNumber, double newPrice) throws SQLException {
        String sql = "UPDATE DINESH.rooms SET rate = ? WHERE room_number = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setDouble(1, newPrice);
        statement.setInt(2, roomNumber);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Room price updated successfully.");
        } else {
            System.out.println("Failed to update room price.");
        }
    }

    
    public void deleteRoom(BufferedReader reader) {
        try {
        	System.out.println("Enter a RoomNumber to delete");
        	int roomNumber=Integer.parseInt(reader.readLine());
            String sql = "DELETE FROM DINESH.rooms WHERE room_number = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomNumber);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Room deleted successfully.");
            } else {
                System.out.println("Failed to delete room.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    
   
}
