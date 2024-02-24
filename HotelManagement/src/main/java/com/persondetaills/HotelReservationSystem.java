package com.persondetaills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.exceptionhandling.CustomException;

/**
 * The HotelReservationSystem class represents the main entry point for the hotel management system.
 * It provides functionality for user interaction, such as sign up, login, and exiting the system.
 * @author Dinesh Karmegam (Expleo)
 * @since  13 FEB 2024
 */
public class HotelReservationSystem {

    /** The JDBC URL for connecting to the database. */
    static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    
    /** The userName for accessing the database. */
    static final String USERNAME = "system";
    
    /** The password for accessing the database. */
    static final String PASSWORD = "dhinesh";

    /** The connection object for database connectivity. */
    static Connection conn;

    /**
     * The main method is the entry point of the hotel management system.
     * It provides a menu-driven interface for users to interact with the system.
     * 
     */
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            try {
                // Load the Oracle JDBC driver and establish a connection to the database
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                System.out.println("Connected to the database.");

                // Create a BufferedReader to read user input
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                Account account = new Account(conn);
                int choice;
                do {
                    // Display the main menu
                	System.out.println("\t******************************************************************************************");
                    System.out.println("\t*                                                                                        *");
                    System.out.println("\t*                    WELCOME TO Breezy Haven Hotel                                       *");
                    System.out.println("\t*                                                                  567,Ocean View Avenue,*");
                    System.out.println("\t*                                                                          Asgard-636004.*");
                	System.out.println("\t******************************************************************************************");
                    System.out.println("\t*    Book your stay, create your memories. Welcome to a world of comfort and luxury      *");
                    System.out.println("\t******************************************************************************************");
                    System.out.println("\t*         1. SIGN UP                                                                     *");
                    System.out.println("\t*         2. LOGIN                                                                       *");
                    System.out.println("\t*         3. GUEST                                                                       *");
                    System.out.println("\t*         4. Exit                                                                        *");
                    System.out.println("\t*                                                                                        *");
                    System.out.println("\t******************************************************************************************");
                    System.out.println("=".repeat(250));
                    System.out.println("Enter your choice: ");
                    try {
                        // Read user choice and perform corresponding actions
                        choice = Integer.parseInt(reader.readLine());
                        switch (choice) {
                            case 1:
                                account.registerUser(reader);
                                break;
                            case 2:
                                account.loginUser(reader);
                                break;
                            case 3:
                                account.guest(reader);
                                break;
                            case 4:
                            	System.out.println("Exiting...");
                                exit = true;
                            	break;
                            default:
                                throw new CustomException("Please Enter a valid Number");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid option (1, 2, 3 or 4).");
                        choice = 0; 
                    }
                } while (!exit && choice != 3);
                conn.close(); // Close the database connection
            } catch (SQLException | IOException | ClassNotFoundException | CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
