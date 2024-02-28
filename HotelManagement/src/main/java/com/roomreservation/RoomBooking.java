package com.roomreservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.enumurations.BookingStatus;
import com.paymentdetails.Payment;
import com.persondetaills.FeedBack;
/**
 * The RoomBooking class represents the process of booking and managing room reservations.
 * It provides methods for booking rooms, canceling bookings, and displaying reservation details.
 *
 * @author Dinesh Karmegam(expleo)
 * @since 21 Feb 2024
 */

public class RoomBooking {
	private String reservationId;
	private String  checkInDate;
	private Room room;
	private String checkOutDate; 
	private Connection conn;

    public RoomBooking(Connection conn) {
        this.conn = conn;
    }
    public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	
    public RoomBooking(Connection conn,Room room, String checkInDate, String checkOutDate) {
    	this.conn=conn;
    	this.room=room;
    	this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		}
    public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	
	
	public void bookRoom(BufferedReader reader,int Id,long noOfDays) throws SQLException {
        try {
            	int  reservedId = generateNewReservationId(conn);
            	String bookRoomSql="INSERT INTO DINESH.reservation  VALUES (?, ?, ?,sysdate,?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(bookRoomSql);            
                statement.setInt(1,reservedId);
                statement.setInt(2,Id);
                statement.setInt(3, room.getRoomnumber());
                statement.setString(4, checkInDate);
                statement.setString(5,checkOutDate );
                statement.setString(6,BookingStatus.BOOKED.toString() );  
                int rowsUpdated = statement.executeUpdate();
                Payment pay=new Payment(conn);
            	boolean condition = pay.processPayment(reader,room.getRoomnumber(),reservedId,noOfDays);
                if (rowsUpdated > 0 && condition==true) {
                    System.out.println("Room booked successfully!");
                    String updateRoomStatusSql = "UPDATE DINESH.rooms SET Available = ? WHERE room_number = ?";
                    PreparedStatement updateStatement = conn.prepareStatement(updateRoomStatusSql);
                    updateStatement.setString(1,BookingStatus.BOOKED.toString());
                    updateStatement.setInt(2, room.getRoomnumber());
                    int roomsUpdated = updateStatement.executeUpdate();
                    if (roomsUpdated > 0) {
                        System.out.println("Booked details");
                    } else {
                        System.out.println("Failed to update room status");
                    }
                    displayReservationDetails(reservedId);
                    System.out.println("Would you like to provide feedback ? (Yes/No)");
                    String feedbackOption = reader.readLine();
                    if (feedbackOption.equalsIgnoreCase("Yes")) {
                    	System.out.println("Enter rating 1 to 5");
                    	int rating=Integer.parseInt(reader.readLine());
                    	System.out.println("Enter Comments:");
                    	String comments=reader.readLine();
                    	FeedBack feedback= new FeedBack(conn,reservedId,rating,comments);
                    	feedback.ProvideFeedback();
                    	
                    }
                } 
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        } catch (IOException e) {
        	System.out.println(e.getMessage());

		}
    }

    
    
    public int generateNewReservationId(Connection conn) {
        try {     
            String maxReservationIdQuery = "SELECT MAX(reservation_id) FROM DINESH.reservation";
            PreparedStatement maxIdStatement = conn.prepareStatement(maxReservationIdQuery);    
            ResultSet resultSet = maxIdStatement.executeQuery();
            int maxReservationId = 0;
            if (resultSet.next()) {
                maxReservationId = resultSet.getInt(1);
            }
            int newReservationId;
            if (maxReservationId != 0) {
                int maxId = maxReservationId; 
                newReservationId = maxId+1; 
            } else {              
                newReservationId = 100;
            }          
            return newReservationId;
        } catch (SQLException e) {
            e.printStackTrace();
             
        }
		return 0;
    }

    private void displayReservationDetails(int reservationId) throws SQLException {
        String reservationDetailsSql =" SELECT r.reservation_id, r.room_number, r.reserved_date, r.check_in_date, r.check_out_date, r.status, p.payment_id, p.reservation_id AS payment_reservation_id, p.amount, p.payment_date, p.payment_type, p.payment_status FROM DINESH.reservation r JOIN DINESH.payment p ON r.reservation_id = p.reservation_id WHERE r.reservation_id = ?";


        PreparedStatement statement = conn.prepareStatement(reservationDetailsSql);
        statement.setInt(1, reservationId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println("\t***********************************************************************");
            System.out.println("\t*       Reservation Details                                           ");
            System.out.println("\t***********************************************************************");
            System.out.println("\t- Reservation ID: " + resultSet.getInt("reservation_id") + "        ");
            System.out.println("\t- Room Number: " + resultSet.getInt("room_number") + "              ");
            System.out.println("\t- Check-in Date: " + resultSet.getString("check_in_date") + "       ");
            System.out.println("\t- Check-out Date: " + resultSet.getString("check_out_date") + "     ");
            System.out.println("\t- Status: " + resultSet.getString("status") + "                     ");
            System.out.println("\t- Payment ID: " + resultSet.getInt("payment_id") + "                ");
            System.out.println("\t- Payment Amount: " + resultSet.getDouble("amount") + "             ");
            System.out.println("\t- Payment Date: " + resultSet.getString("payment_date") + "         ");
            System.out.println("\t- Payment Type: " + resultSet.getString("payment_type") + "          ");
            System.out.println("\t- Payment Status: " + resultSet.getString("payment_status") + "     ");
            System.out.println("\t-**********************************************************************");
        }
    }
    public void cancelBooking(int reservationId,Connection connect) {
        try {
            String cancelBookingSql = "UPDATE DINESH.reservation SET status = ?	WHERE reservation_id = ? AND status = 'BOOKED'";
            PreparedStatement statement = connect.prepareStatement(cancelBookingSql);
            statement.setString(1, BookingStatus.CANCELED.toString());
            statement.setInt(2, reservationId);
                
            String modifyRoom = "UPDATE DINESH.rooms SET AVAILABLE= ? WHERE room_number = ?";
            PreparedStatement modRoom = connect.prepareStatement(modifyRoom);
            modRoom.setString(1, BookingStatus.AVAILABLE.toString());
            modRoom.setInt(2,fetchRoomNumber(reservationId,connect) );
            int roomModification=modRoom.executeUpdate();
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0 && roomModification>0) {
                System.out.println("Booking canceled successfully!");
                System.out.println("=========================================================\n"
                		        + "|       Notification                                      |\n"
                	         	+ "===========================================================\n"
                	        	+ "|   Your payment will be  refunded within  4 working days.|\n"
                	         	+ "===========================================================\n"+"");
                	                                         	
            } else {
                System.out.println("Failed to cancel booking.");
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int fetchRoomNumber(int reservationId,Connection connect) {
        int roomNo = -1; 
        
        try {
            String fetchRoomNumberSql = "SELECT room_number FROM DINESH.reservation WHERE reservation_id = ?";
                                            
            PreparedStatement statement = connect.prepareStatement(fetchRoomNumberSql);
            statement.setInt(1, reservationId);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                roomNo = resultSet.getInt("room_number");
            } else {
                System.out.println("No room found for reservation ID: " + reservationId);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return roomNo;
    }



} 

