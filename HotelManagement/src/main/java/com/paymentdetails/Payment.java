package com.paymentdetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.enumurations.PaymentStatus;
import com.enumurations.PaymentType;
import com.exceptionhandling.CustomException;
/**
 * The Payment class represents the process of handling payments for room reservations.
 * It provides methods for processing payments using credit or debit cards, fetching room prices,
 * and generating unique payment IDs.
 *
 * @author Dinesh Karmegam (expleo)
 * @since 21 Feb 2024
 */
public class Payment {
	private static Connection conn;
	private int paymentId;
	private int reservationId;
    private int amount;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    @SuppressWarnings("static-access")
	public Payment(Connection conn) {
        this.conn = conn;
    }
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	public boolean processPayment(BufferedReader reader, int roomNumber, int reservedId, long noOfDays) {
	    try {
	        double totalAmount = fetchRoomPrice(roomNumber) * noOfDays;

	        System.out.println("Total amount want to be paid: " + totalAmount);
            boolean condition = true;
	        while (condition) {
	            System.out.println("\t*************************************");
	            System.out.println("\t*                                   *");
	            System.out.println("\t*       PAYMENT METHOD MENU         *");
	            System.out.println("\t*                                   *");
	            System.out.println("\t*  1. Credit Card                   *");
	            System.out.println("\t*  2. Debit Card                    *");
	            System.out.println("\t*  3. Exit                          *");
	            System.out.println("\t*                                   *");
	            System.out.println("\t*************************************");
	            System.out.print("Enter your choice: ");

	            String paymentMethod = "";
	            int paymentId = generateNewPaymentId(conn);
	            boolean paymentSuccessful = false;

	            try {
	                int paymentOption = Integer.parseInt(reader.readLine());

	                switch (paymentOption) {
	                    case 1:
	                        System.out.println("Credit Card payment selected. Enter card details:");
	                        inputCardDetails(reader);
	                        paymentMethod = PaymentType.Credit.toString();
	                        paymentSuccessful = true;
	                        break;
	                    case 2:
	                        System.out.println("Debit Card payment selected. Enter card details:");
	                        inputCardDetails(reader);
	                        paymentMethod = PaymentType.Debit.toString();
	                        paymentSuccessful = true;
	                        break;
	                    case 3:
	                    	condition =false;
	                        System.out.println("Payment cancelled.");
	                        return false;
	                    default:
	                        System.out.println("Invalid choice. Please enter a valid option.");
	                        break;
	                }

	                if (paymentSuccessful) {
	                    try {
	                        String insertQuery = "INSERT INTO DINESH.payment VALUES (?, ?, ?, sysdate, ?, ?)";
	                        PreparedStatement statement = conn.prepareStatement(insertQuery);
	                        statement.setInt(1, paymentId);
	                        statement.setInt(2, reservedId);
	                        statement.setDouble(3, totalAmount);
	                        statement.setString(4, paymentMethod);
	                        statement.setString(5, PaymentStatus.PAID.toString());

	                        int rowsInserted = statement.executeUpdate();
	                        if (rowsInserted > 0) {
	                        	System.out.println("\t============================================================");
	                            System.out.println("\t|                Notification                               |");
	                            System.out.println("\t============================================================");
	                            System.out.println("\t"+LocalDate.now()+"                                         |");
	                            System.out.println("\t|   Payment successful! Thank you for your reservation.     |");
	                            System.out.println("\t============================================================");
	                            return true;
	                        } else {
				                throw new CustomException("Failed to process the payment.");

	                        }
	                    } catch (SQLException e) {
	                    	System.out.println(e.getMessage());
	                    }
	                    return paymentSuccessful;
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid choice. Please enter a number.");
	            }
	            catch(CustomException e) {
	            	System.out.println(e.getMessage());
	            }
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
		return false;
	    
	}

	public static  double fetchRoomPrice(int roomNumber) throws SQLException {
        String query = "SELECT rate FROM DINESH.rooms WHERE room_number = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, roomNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("rate");
                } else {
                    throw new SQLException("Room with number " + roomNumber + " not found.");
                }
            }
        }
    }
	
	
	public int generateNewPaymentId(Connection conn) {
	    try {
	        String maxPaymentIdQuery = "SELECT MAX(payment_id) FROM DINESH.payment";
	        PreparedStatement maxIdStatement = conn.prepareStatement(maxPaymentIdQuery);

	        ResultSet resultSet = maxIdStatement.executeQuery();
	        int maxPaymentId = 0;
	        if (resultSet.next()) {
	            maxPaymentId = resultSet.getInt(1);
	        }

	        int newPaymentId;
	        if (maxPaymentId != 0) {
	            newPaymentId = maxPaymentId + 1;
	        } else {
	            newPaymentId = 10000; 
	        }

	        return newPaymentId;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	
	private void inputCardDetails(BufferedReader reader) throws IOException {
	    String cardNumberRegex = "\\d{14}";
	    String cardHolderNameRegex = "[a-zA-Z]*";
	    String expirationDateRegex = "^(0[1-9]|1[0-2])\\/?(\\d{4}|\\d{2})$";
	    String cvvRegex = "\\d{3}";

	    boolean validInput = false;
	    while (!validInput) {
	        try {
	            System.out.println("Enter Card Number (14 digits):");
	            cardNumber = reader.readLine();
	            if (!cardNumber.matches(cardNumberRegex)) {
	                throw new CustomException("Entered cardNumber is Not Valid. Please enter 14 digits.");
	            }

	            System.out.println("Enter Card Holder Name:");
	            cardHolderName = reader.readLine();
	            if (!cardHolderName.matches(cardHolderNameRegex)) {
	                throw new CustomException("Entered a Valid Holder Name.");
	            }

	            System.out.println("Enter Card expirationDate (MM/YYYY):");
	            expirationDate = reader.readLine();
	            if (!expirationDate.matches(expirationDateRegex)) {
	                throw new CustomException("Entered a Valid Date Format (MM/YYYY).");
	            }

	            System.out.println("Enter Card CVV (3 digits):");
	            cvv = reader.readLine();
	            if (!cvv.matches(cvvRegex)) {
	                throw new CustomException("Invalid CVV format. Please enter 3 digits.");
	            }
	            validInput = true;
	        } catch (CustomException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	}


}

