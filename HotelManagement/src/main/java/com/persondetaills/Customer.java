package com.persondetaills;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Represents a customer in the hotel management system.
 * 
 * This class extends the functionality of the Person class to include customer-specific details
 * such as booking rooms, managing reservations, and handling personal information.
 * 
 @author Dinesh Karmegam(expleo)
 *@since  21 Feb 2024
 */
public class Customer extends Person {
    private int customerId;
   
	public Customer(int customerId,String firstName, String lastName,int age, String gender,  String dob, String phone,String email,
			String address,String zipcode, Account account) {
		super(firstName,lastName,age,gender,dob,phone,email,address,zipcode,account);
		this.customerId=customerId;
	}
		

	public Customer(Connection conn) {		
		super(conn);
	}


	public int getCustomerID() {
        return customerId;
    }

    public void setCustomerID(int customerID) {
        this.customerId = customerID;
    }
  
   

    
    
    public void updateEmail(Customer cus) {
        try {
            String updateQuery = "UPDATE DINESH.customer SET email = ? WHERE customer_id = ?";
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, cus.getEmail());
            statement.setInt(2, cus.getCustomerID());
            statement.executeUpdate();
            System.out.println("updated sucessfully");
        } catch (SQLException e) {
            System.err.println("Error updating email: " + e.getMessage());
        }
    }

    public void updateAddress(Customer cus) {
        try {
            String updateQuery = "UPDATE DINESH.customer SET address = ? WHERE customer_id = ?";
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, cus.getAddress());
            statement.setInt(2, cus.getCustomerID());
            statement.executeUpdate();
            System.out.println("updated sucessfully");
        } catch (SQLException e) {
            System.err.println("Error updating address: " + e.getMessage());
        }
    }

    public void updatePassword(Customer cus) {
        try {
            String updateQuery = "UPDATE DINESH.account  SET mobile_number = ? WHERE customer_id = ?";
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, cus.getPhone());
            statement.setInt(2, cus.getCustomerID());
            statement.executeUpdate();
            System.out.println("updated sucessfully");
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
    }
    
    
    public void updateMobileNumber(Customer cus) {
        try {
            String updateQuery = "UPDATE DINESH.customer SET mobile_number = ? WHERE customer_id = ?";
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, cus.getPhone());
            statement.setInt(2, cus.getCustomerID());
            statement.executeUpdate();
            System.out.println("updated sucessfully");
        } catch (SQLException e) {
            System.err.println("Error updating mobile number: " + e.getMessage());
        }
    }


}
