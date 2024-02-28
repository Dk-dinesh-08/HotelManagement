package com.persondetaills;
	
import com.search.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
//imported packages
import com.enumurations.AccountType;
import com.enumurations.RoomType;
import com.exceptionhandling.CustomException;
import com.roomreservation.Room;
import com.roomreservation.RoomBooking;
/**
 * Represents a hotel management account.
 * 
 * This class provides functionalities to manage an account associated with hotel management,
 * including deposit, withdrawal, and balance inquiry.
 * 
 * @author Dinesh Karmegam (expleo)
 * @since FEB 20 2024
 */
	public class Account {
		
	    private String userName;	
	    private String password;
		private Connection conn;
        RoomBooking roomBooking = new RoomBooking(conn);
        FeedBack feed =new FeedBack(conn);
        Search search = new Room(conn);

		InputValidator validate= new InputValidator(conn);
		public Account(String userName,String password) {
			this.userName=userName;
			this.password=password;
		}
	    public Account(Connection conn) {
	        this.conn = conn;
	    }  
	    public String getUserName() {
	    	return userName;
	    }
	    public void setUserName(String userName) {
	    	this.userName = userName;
	    }
	    public String getPassword() {
	    	return password;
	    }
	    public void setPassword(String password) {
	    	this.password = password;
	    }
	    
	    public void registerUser(BufferedReader reader) throws SQLException {
	        try {
	        	System.out.println("Note : Registration is only available for customers");
                System.out.println("=".repeat(250));
                
                String firstName=null;
	            boolean isValidName = false;
	            do {
	            	try {
	    	        System.out.println("Enter first name:");
	                firstName = reader.readLine();
	                if (!validate.isValidFirstName(firstName)) {	                	
		                   throw new CustomException("You may have entered an incorrect Dob");
	                } else {
	                    isValidName = true;
	                }
	            	}catch (CustomException e) {
		            	System.out.println(e.getMessage());
					}
	            	
	            } while (!isValidName);
	           	
	            String lastName = null;
	            boolean isValidLast = false;
	            do {
	                try {
	                    System.out.println("\nEnter last name:");
	                    lastName = reader.readLine();
	                    if (!validate.isValidLastName(lastName)) {	                	
	                        throw new CustomException("You may have entered an incorrect last name.");
	                    } else {
	                        isValidLast = true;
	                    }
	                } catch (CustomException e) {
	                    System.out.println(e.getMessage());
	                }
	            } while (!isValidLast);

	            
	            String gender = null;
	            boolean isValidgen = false;
	            do {
	                try {
	                    System.out.println("\nEnter gender (male/female):");
	                    gender = reader.readLine();
	                    if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
	                        throw new CustomException("Please enter either 'male' or 'female'.");
	                    } else {
	                        isValidgen = true;
	                    }
	                } catch (IOException e) {
	                    System.out.println("Error reading input. Please try again.");
	                } catch (CustomException e) {
	                    System.out.println(e.getMessage());
	                }
	            } while (!isValidgen);

	            
		            String dob = null;
		            int age=0;
		            boolean isValidDOB = false;
		            do {
		                try {
		                    System.out.println("\nEnter date of birth (YYYY-MM-DD) :");
		                    dob = reader.readLine();
		                    if (!validate.isValidDateOfBirth(dob)) {
		                        throw new CustomException("Age must be at least 20 years old.");
		                    } else {
		                        LocalDate birthDate = LocalDate.parse(dob);
		                        LocalDate currentDate = LocalDate.now();
		                        age = Period.between(birthDate, currentDate).getYears();

		                        if (age >= 20) {
		                            System.out.println("Age is: " + age);
		                            isValidDOB = true;
		                        } else {
		                            throw new CustomException("Age must be at least 20 years old.");
		                        }
		                    }
		                } catch (CustomException e) {
		                    System.out.println(e.getMessage());
		                } catch (IOException e) {
		                    System.out.println("Error reading input. Please try again.");
		                }
		            } while (!isValidDOB);
		        
	            
                String email=null;
	            boolean isValidEmail = false;
	            do {
	            	try {
	                System.out.println("\nEnter email:");
	                System.out.println("Note: Please enter a valid email address like Dineshkumar@gmail.com");
	                email = reader.readLine();
	                if (!validate.isValidEmail(email)) {
		                   throw new CustomException("You may have entered an incorrect email format. Please ensure it follows the correct format.");
	                } else {
	                    isValidEmail = true;
	                }
	            	}catch (CustomException e) {
		            	System.out.println(e.getMessage());
					}
	            	
	            } while (!isValidEmail);
	            
       
	            String phone=null;
	            boolean isValidPhone = false;
	            do {try {
	                System.out.println("\nEnter phone number (Should be 10 digits):");
	                phone = reader.readLine();
	                if (!validate.isValidPhoneNumber(phone)) {
		                   throw new CustomException("You may have entered an incorrect Phone format. Please ensure it follows the correct format.");
	                } else {
	                    isValidPhone = true;
	                }
	             }
	               catch (CustomException e) {
		            	System.out.println(e.getMessage());
					}       
	            } while (!isValidPhone);
	            
	            System.out.println("\nEnter address:");
	            String address = reader.readLine();
	
	            
	            String zipCode=null;
	            boolean isValidZip = false;
	            do {try {
	                System.out.println("\nEnter zipcode number (Should be (636-001)format digits):");
	                zipCode = reader.readLine();
	                if (!validate.isValidZipCode(zipCode)) {
		                   throw new CustomException("You may have entered an incorrect ZipCode format. Please ensure it follows the correct format.");
	                } else {
	                	isValidZip = true;
	                }
	             }
	               catch (CustomException e) {
		            	System.out.println(e.getMessage());
					}       
	            } while (!isValidZip);
	            
	            boolean isValidUser= false;
	            String UserName = null ;
	            do {
	                try {
	                    System.out.println("\nUsername must start with a letter and may be followed by numbers; special characters are not allowed.");
	                     UserName = reader.readLine();

	                    if (!validate.isValidUsername(UserName)) {
	                        throw new CustomException("You may have entered an incorrect UserName format. Please ensure it follows the correct format.");
	                    } else {
	                        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM DINESH.account WHERE user_name = ?");
	                        preparedStatement.setString(1, UserName);
	                        ResultSet resultSet = preparedStatement.executeQuery();
	                        if (resultSet.next()) {
	                            throw new CustomException("Username already exists. Please choose a different one.");
	                        } else {
	                            isValidUser = true;
	                        }
	                    }
	                } catch (IOException | CustomException | SQLException e) {
	                    System.out.println(e.getMessage());
	                }
	            } while (!isValidUser);
	            
	            String password="";
	            boolean isValidPassword = false;
	            do {try {
	                System.out.println("\nEnter Password should contains at least contain one uppercase and one lowercase and one number ");
	                password = reader.readLine();
	                if (!validate.isValidPassword(password)) {
		                   throw new CustomException("You may have entered an incorrect UserName format. Please ensure it follows the correct format.");
	                } else {
	                	isValidPassword = true;
	                }
	             }
	               catch (CustomException e) {
		            	System.out.println(e.getMessage());
					}       
	            } while (!isValidPassword);

	            Account acc = new Account(UserName,password);
	            
	            String maxPersonIdQuery = "SELECT MAX(customer_id) FROM DINESH.customer";
	            PreparedStatement maxIdStatement = conn.prepareStatement(maxPersonIdQuery);
	            ResultSet resultSet = maxIdStatement.executeQuery();
	            int maxPersonId = 0;
	            if (resultSet.next()) {
	                maxPersonId = resultSet.getInt(1);
	            }
	            int Id = maxPersonId + 1;
	            insertRegisteredUsers(new Customer(Id,firstName, lastName,age, gender,dob,phone,email,
	        			address,zipCode,acc));
	        } catch (IOException e) {
	        	System.out.println(e.getMessage());
	        } catch (SQLException e) {
				System.out.println(e.getMessage());}
	    }
	    public void insertRegisteredUsers(Customer customer) {
	        try {
	            String customerSql = "INSERT INTO DINESH.customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	            PreparedStatement customerStatement = conn.prepareStatement(customerSql);
	
	            String accountSql = "INSERT INTO DINESH.account (user_name, password,customer_id,ACCOUNT_TYPE) VALUES (?, ?, ?, ?)";
	
	            PreparedStatement accountStatement = conn.prepareStatement(accountSql);               
	                customerStatement.setInt(1, customer.getCustomerID());
	                customerStatement.setString(2, customer.getFirstName());
	                customerStatement.setString(3, customer.getLastName());
	                customerStatement.setInt(4, customer.getAge());
	                customerStatement.setString(5, customer.getGender());
	                customerStatement.setString(6, customer.getPhone());
	                customerStatement.setString(7, customer.getEmail());
	                customerStatement.setString(8, customer.getDob());
	                customerStatement.setString(9, customer.getAddress());
	                customerStatement.setString(10, customer.getZipCode());
	                int customerRowsInserted = customerStatement.executeUpdate();
	                accountStatement.setString(1, customer.getUserName());
	                accountStatement.setString(2, customer.getPassword());
	                accountStatement.setInt(3, customer.getCustomerID()); 
	                accountStatement.setString(4, AccountType.CUSTOMER.toString());
	                int accountRowsInserted = accountStatement.executeUpdate();
	                if (customerRowsInserted > 0 && accountRowsInserted > 0) {
	                	System.out.println("=".repeat(200));
	                    System.out.println("\nCustomer Registered successfully!");
	                } else {
	                    System.out.println("Sorry Registration Failed");
	                }	            
	        } catch (SQLException e) {
	            System.out.println("UserName is already taken");
	        }
	    }
	   
	   
	    
	    public void loginUser(BufferedReader reader) {
	    	 boolean con = true;
	    	 while (con){
	    		 try {
	                System.out.println("=".repeat(250));
	                System.out.println("\t=================================");
	                System.out.println("\t|         USER  OPTIONS          |");
	                System.out.println("\t=================================");
	                System.out.println("\t|  1. Admin                     |");
	                System.out.println("\t|  2. Customer                  |");
	                System.out.println("\t|  3. Go back                   |");
	                System.out.println("\t|  4. Exit                      |");
	                System.out.println("\t|                               |");
	                System.out.println("\t=================================");

	                System.out.println("=".repeat(250));
	                System.out.print("Enter your choice: ");
	                int userType = Integer.parseInt(reader.readLine());
	                switch (userType) {
	                    case 1:
	                        loginAdmin(reader);
	                        break;
	                    case 2:
	                        loginCustomer(reader);
                            break;
	                    case 3:
	                        con = false;
	                        break;
	                    case 4:
	                    	System.out.println("Are You Sure Want To Exit (1.Quit 2.Continue)");
	                    	int i= Integer.parseInt(reader.readLine());
	                    	if(i==1) {
	                    	System.exit(0);}
	                    	else {
	                    		continue;
	                    	}
	                    default:
	                        System.out.println("Invalid choice. Please enter a valid option (1, 2, or 3).");
	                        break;
	                }
	            }
	        catch(NumberFormatException e) {
                System.out.println("Please enter a valid option (1, 2, or 3).");

	        }
	        catch (IOException e) {
	        	System.out.println(e.getMessage());
	        }
	    }
}
	    private void loginAdmin(BufferedReader reader) {
	    	
	        boolean continueLogin = true;
	        while (continueLogin) {
	            try {	             
	            	System.out.println("=".repeat(250));
	            	System.out.println("Username must start with a letter and may be followed by numbers; special characters are not allowed.");
	                String username = reader.readLine();
	                int Id = getUserId(username, 1);
	                System.out.println("\nEnter password: ");
	                String password = reader.readLine();
	                String sql = "SELECT * FROM DINESH.account WHERE user_name = ? AND password = ? AND account_type='ADMIN'";
	                PreparedStatement statement = conn.prepareStatement(sql);
	                statement.setString(1, username);
	                statement.setString(2, password);
	                ResultSet resultSetOne = statement.executeQuery();
	                if (resultSetOne.next()) {
	                    System.out.println("===========================================================Admin Details====================================================================================");
	                    ResultSet resultSet1 = getAdminResultSet(Id);
	                    if (resultSet1.next()) {
	    	                System.out.println("=".repeat(250));
	                        System.out.println("\t***********************************************");
	                        System.out.println("\t # Admin ID: " + resultSet1.getInt(1) + "         ");
	                        System.out.println("\t # First Name: " + resultSet1.getString(2) + "    ");
	                        System.out.println("\t # Last Name: " + resultSet1.getString(3) + "     ");
	                        System.out.println("\t # Age: " + resultSet1.getInt(4) + "              ");
	                        System.out.println("\t # Gender: " + resultSet1.getString(5) + "        ");
	                        System.out.println("\t # Email: " + resultSet1.getString(6) + "         ");
	                        System.out.println("\t # Date of Birth: " + resultSet1.getString(7) + " ");
	                        System.out.println("\t # Phone: " + resultSet1.getString(8) + "         ");
	                        System.out.println("\t # Address: " + resultSet1.getString(9) + "       ");
	                        System.out.println("\t # Zip Code: " + resultSet1.getString(10) + "     ");
	                        System.out.println("\t************************************************");
	    	                System.out.println("=".repeat(250));

	                    }
	                    
	                    boolean condition = true;
	                    while (condition) {
	    	                System.out.println("=".repeat(250));
	    	                System.out.println("\t=================================");
	    	                System.out.println("\t|         Operations            |");
	    	                System.out.println("\t=================================");
	    	                System.out.println("\t|  1. Manage Rooms              |");
	    	                System.out.println("\t|  2. Search                    |");
	    	                System.out.println("\t|  3. Book Room                 |");
	    	                System.out.println("\t|  4. veiw feedback             |");
	    	                System.out.println("\t|  5. Go Back                   |");
	    	                System.out.println("\t|  6. Exit                      |");
	    	                System.out.println("\t|                               |");
	    	                System.out.println("\t=================================");

	    	                System.out.println("=".repeat(250));
	    	                System.out.println("Enter a Choice ");
	                        int number = Integer.parseInt(reader.readLine());
	                        switch (number) {
	                            case 1:
	                                adminOperations(reader);
	                                break;
	                            case 2:
	                                performSearch(reader, search, Id);
	                                break;
	                            case 3:
		                            roomBook(reader, Id);
	                                break;
	                            case 4:
	                            	feed.fetchFeedback(conn);
	                            	break;
	                            case 5:
	                            	condition=false;
	                            	break;
	                            case 6:
	                            	System.out.println("Are You Sure Want To Exit (1.Quit 2.Continue)");
	    	                    	int i= Integer.parseInt(reader.readLine());
	    	                    	if(i==1) {
	    	                    	System.exit(0);}
	    	                    	else {
	    	                    		continue;
	    	                    	}
	                            	break;
	                            default:
	                                throw new CustomException("You May Entered wrong Number Please enter a valid option ");
	                        }
	                    }
	                } else {
	                    System.out.println("Invalid username or password.");
	                }
	            } catch (IOException | SQLException | NumberFormatException | CustomException e) {
	                System.err.println(e.getMessage());
	            }
	            System.out.println("Warning : Do you want to continue logging in? (yes/no)");
	            try {
	                String continueChoice = reader.readLine();
	                if (!continueChoice.equalsIgnoreCase("yes")) {
	                    continueLogin = false;
	                }
	            } catch (IOException e) {
	                System.err.println("Error reading input: " + e.getMessage());
	            }
	        }
	    }

	    private void loginCustomer(BufferedReader reader) {
	        try {
	            System.out.println("=".repeat(250));
	            System.out.println("Enter username: ");
	            String username = reader.readLine();
	            int Id = getUserId(username, 2);
	            System.out.println("\nEnter password: ");
	            String password = reader.readLine();
	            String sql = "SELECT * FROM DINESH.account WHERE user_name = ? AND password = ? AND account_type='CUSTOMER'";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, username);
	            statement.setString(2, password);
	            ResultSet resultSetOne = statement.executeQuery();
	            if (resultSetOne.next()) {
	                ResultSet result = getCustomerResultSet(Id);
	                if (result.next()) {
	                    System.out.println("=".repeat(250));
		                System.out.println("------------------------------------------------------["+result.getString(1)+" login successful!]----------------------------------------------------");

	                    System.out.println("\t*************************************************");
	                    System.out.println("\t            Details                              ");
	                    System.out.println("\t*************************************************");
	                    System.out.println("\t + First Name: " + result.getString(1)+"         ");
	                    System.out.println("\t + Last Name: " + result.getString(2)+"          ");
	                    System.out.println("\t + Age: " + result.getInt(3)+"                   ");
	                    System.out.println("\t + Gender: " + result.getString(4)+"             ");
	                    System.out.println("\t + Phone: " + result.getString(5)+"              ");
	                    System.out.println("\t + Email: " + result.getString(6)+"              ");
	                    System.out.println("\t + DOB: " + result.getString(7)+"                ");
	                    System.out.println("\t + Address: " + result.getString(8)+"            ");
	                    System.out.println("\t + Zip Code: " + result.getString(9)+"          ");
	                    System.out.println("\t*************************************************");
	                    System.out.println("=".repeat(250));
	                    Search search1 = new Room(conn);
	                    performSearch(reader, search1, Id);
	                }
	            } else {
	                System.out.println("Invalid username or password.");
	            }
	        } catch (IOException | SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    private int getUserId(String username, int userType) throws SQLException {
	        String query;
	        if (1==userType) {
	            query = "SELECT admin_id FROM DINESH.account WHERE user_name = ?";
	        } else if (2==userType) {
	            query = "SELECT customer_id FROM DINESH.account WHERE user_name = ?";
	        } else {
	            throw new IllegalArgumentException("Invalid user type: " + userType);
	        }
	        PreparedStatement preparedStatement = conn.prepareStatement(query);
	        preparedStatement.setString(1, username);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        int Id = 0;
	        if (resultSet.next()) {
	            Id = resultSet.getInt(1);
	        }
	        return Id;
	    }

	    public ResultSet getCustomerResultSet(int customerId) throws SQLException {
	        String query = "SELECT FIRST_NAME,LAST_NAME,ADMIN_AGE,GENDER,PHONE,EMAIL,DOB,ADDRESS,ZIP_CODE FROM DINESH.customer WHERE customer_id = ?";
	        PreparedStatement preparedStatement = conn.prepareStatement(query);
	        preparedStatement.setInt(1, customerId);
	        return preparedStatement.executeQuery();
	    }
	    
	    public ResultSet getAdminResultSet(int adminId) throws SQLException {
	        String query = "SELECT * FROM DINESH.admin WHERE admin_id = ?";
	        PreparedStatement preparedStatement = conn.prepareStatement(query);
	        preparedStatement.setInt(1, adminId);
	        return preparedStatement.executeQuery();
	    }

	   
	    private void performSearch(BufferedReader reader, Search search, int Id) {
	        try {
	            boolean running = true;
	            while (running) {
	                try {
		                System.out.println("=".repeat(250));
		                System.out.println("\t=====================================");
		                System.out.println("\t|         SEARCH OPTIONS             |");
		                System.out.println("\t=====================================");
		                System.out.println("\t|     1. Search                      |");
		                System.out.println("\t|     2. Room Book                   |");
		                System.out.println("\t|     3. Cancel Booking              |");
		                System.out.println("\t|     4. Check App rating            |");
		                System.out.println("\t|     5. View Profile                |");
		                System.out.println("\t|     6. Update Profile              |");
		                System.out.println("\t|     7. Booking History             |");
		                System.out.println("\t|     8. Go Back                     |");
		                System.out.println("\t|     9. Exit                        |");
		                System.out.println("\t|                                    |");
		                System.out.println("\t=====================================");

		                System.out.println("=".repeat(250));
	                    System.out.println("Enter a choice:");
	                    String input = reader.readLine();
	                    int num;
	                    try {
	                        num = Integer.parseInt(input);
	                    } catch (NumberFormatException e) {
	                        throw new CustomException("Invalid input. Please enter a number.");
	                    }
	                    switch (num) {
	                        case 1:
	                            performSearchOptions(reader, search, roomBooking);
	                            break;
	                        case 2:
	                            roomBook(reader, Id);
	                            break;
	                        case 3:
	                        	 cancelBook(Id,reader,roomBooking);	  
	                        	 break;
	                        case 4:
                            	feed.fetchFeedback(conn);
	                        case 5:
	                            displayRegisteredUserDetails(Id);
	                            break;
	                        case 6:
	                        	Customer cus=new Customer(conn);
	                        	 setCustomerDetails(cus, reader,Id);
	                        	break;
	                        case 7:
	                        	displayReservationDetails(Id);
	                        	break;
	                        case 8:
	                            running = false;
	                            break;
	                        case 9:
	                        	System.out.println("Are You Sure Want To Exit (1.Quit 2.Continue)");
    	                    	int i= Integer.parseInt(reader.readLine());
    	                    	if(i==1) {
    	                    	System.exit(0);}
    	                    	else {
    	                    		continue;
    	                    	}
                            	break;
	                        default:
	                            throw new CustomException("Invalid input. Please enter a valid option.");
	                    }
	                } catch (CustomException | SQLException e) {
	                    System.out.println(e.getMessage());
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Error reading input: " + e.getMessage());
	        }
	    }
	    public void roomBook(BufferedReader reader, int Id) {
		    try {
		        displayAllRooms();
		        System.out.println("Enter Room Type to book: ");
		        boolean validChoice=false;
		        String roomType="";
		        while (!validChoice) {
		            System.out.println("\t============================================"); 
		            System.out.println("\t|   Enter Room Type to search              |");
		            System.out.println("\t============================================"); 
		            System.out.println("\t|    1. Single                             |");
		            System.out.println("\t|    2. Double                             |");
		            System.out.println("\t|    3. Suite                              |");
		            System.out.println("\t|    4. Go Back                            |");
		            System.out.println("\t============================================"); 
		            System.out.println("Enter a choice:");
		            String input = reader.readLine();
		            try {
		                int roomTypeChoice = Integer.parseInt(input);
		                if (roomTypeChoice >= 1 && roomTypeChoice <= 3) {
		                    System.out.println("1. AC\n2. Non-AC");
		                    int acChoice = Integer.parseInt(reader.readLine());
		                    switch (roomTypeChoice) {
		                        case 1:
		                            roomType = (acChoice == 1) ? "SINGLE_AC" : "SINGLE_NON_AC";
		                            break;
		                        case 2:
		                            roomType = (acChoice == 1) ? "DOUBLE_AC" : "DOUBLE_NON_AC";
		                            break;
		                        case 3:
		                            roomType = (acChoice == 1) ? "SUITE_AC" : "SUITE_NON_AC";
		                            break;
		                    }
		                    validChoice = true;
		                } else if (roomTypeChoice == 4) {
		                    validChoice = false;
		                } else {
		                    System.out.println("Invalid input. Please enter a valid option.");
		                }
		            } catch (NumberFormatException e) {
		                System.out.println("Invalid input. Please enter a number.");
		            }
		        }

		    
		        int roomNumber = fetchRoomNumberByType(roomType);
		        if(!(roomNumber > 0)) {
		        	throw new CustomException("Sorry room is Unavailable");
		        		}
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        LocalDate currentDate = LocalDate.now();

		        LocalDate checkIn;
		        String checkInDate = null;
		        do {
		            System.out.println("Enter a check-in date (YYYY-MM-DD):");
		            checkInDate = reader.readLine();
		            try {
		                checkIn = LocalDate.parse(checkInDate, formatter);
		                if (checkIn.isBefore(currentDate)) {
		                    throw new CustomException("Check-in date cannot be before the current date.");
		                }
		                break; 
		            } catch (DateTimeParseException e) {
		                System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
		            } catch (CustomException e) {
		                System.out.println(e.getMessage());
		            }
		        } while (true);

		        LocalDate checkOut;
		        String checkOutDate=null;
		        do {
		            System.out.println("Enter a check-out date (YYYY-MM-DD):");
		            checkOutDate = reader.readLine();
		            try {
		                checkOut = LocalDate.parse(checkOutDate, formatter);
		                if (checkOut.isBefore(checkIn)) {
		                    throw new CustomException("Check-out date cannot be before the check-in date.");
		                }
		                break; 
		            } catch (DateTimeParseException e) {
		                System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
		            } catch (CustomException e) {
		                System.out.println(e.getMessage());
		            }
		        } while (true);
		        long noOfDays = calculateDaysBetween(checkInDate, checkOutDate);
		        RoomBooking roomBooking = new RoomBooking(conn, new Room(roomNumber), checkInDate, checkOutDate);
		        roomBooking.bookRoom(reader, Id, noOfDays);
		    } catch (SQLException | NumberFormatException | IOException | CustomException e) {
		        System.out.println(e.getMessage());
		    }
		}
	    private void cancelBook(int customerId, BufferedReader reader, RoomBooking roomBooking) throws SQLException, IOException, CustomException {
	        PreparedStatement statement = conn.prepareStatement("SELECT reservation_id FROM DINESH.Reservation WHERE customer_id = ?");
	        statement.setInt(1, customerId);
	        ResultSet resultSet = statement.executeQuery();
	        List<Integer> reservationIds = new ArrayList<>();
	        while (resultSet.next()) {
	            int reservationId = resultSet.getInt("reservation_id");
	            reservationIds.add(reservationId);
	        }
	        if (reservationIds.isEmpty()) {
	            throw new CustomException("Please book a room before attempting to cancel a booking.");
	        } else {
	            System.out.println("Select the reservation ID to cancel:");
	            for (int i = 0; i < reservationIds.size(); i++) {
	                System.out.println((i + 1) + ". " + reservationIds.get(i));
	            }
	            int userInput = Integer.parseInt(reader.readLine());
	            int selectedReservationId = reservationIds.get(userInput - 1);
	            roomBooking.cancelBooking(selectedReservationId,conn);
	        }
	    }

	    private void performSearchOptions(BufferedReader reader, Search search, RoomBooking roomBooking) throws IOException, SQLException {
	        boolean run = true;
	        while (run) {
                System.out.println("=".repeat(250));
                System.out.println("\t======================================");
                System.out.println("\t|            SEARCH OPTIONS          |");
                System.out.println("\t======================================");
                System.out.println("\t|  1. Search by Room Type            |");
                System.out.println("\t|  2. Search by Room Price           |");
                System.out.println("\t|  3. Go Back                        |");
                System.out.println("\t|  4. Exit                           |");
                System.out.println("\t|                                    |");
                System.out.println("\t======================================");

                System.out.println("=".repeat(250));
	            System.out.println("Enter a choice:");
	            String input = reader.readLine();
	            int number;
	            try {
	                number = Integer.parseInt(input);
	            switch (number) {
	                case 1:
	                    searchByRoomType(reader, search, roomBooking);
	                    break;
	                case 2:
	                    searchByRoomPrice(reader, search, roomBooking);
	                    break;
	                case 3:
	                    run = false;
	                    break;
	                case 4:
	                	System.out.println("Are You Sure Want To Exit (1.Quit 2.Continue)");
                    	int i= Integer.parseInt(reader.readLine());
                    	if(i==1) {
                    	System.exit(0);}
                    	else {
                    		continue;
                    	}
                    	break;
	                default:
	                    throw new CustomException("Invalid input. Please enter a valid option.");
	            }
	          }catch (NumberFormatException e) {
	            	System.out.println("Invalid input. Please enter a number.");
	            } catch (CustomException e) {
	            	System.out.println(e.getMessage());
		    	}
	        }
	    }
	    private void searchByRoomType(BufferedReader reader, Search search, RoomBooking roomBooking) throws IOException, CustomException {
	        RoomType roomType = null;
	        boolean validChoice = false;
	        
	        while (!validChoice) {
	            System.out.println("=".repeat(250));
	            System.out.println("\t============================================"); 
	            System.out.println("\t|   Enter Room Type to search              |");
	            System.out.println("\t============================================"); 
	            System.out.println("\t|    1. Single                             |");
	            System.out.println("\t|    2. Double                             |");
	            System.out.println("\t|    3. Suite                              |");
	            System.out.println("\t|    4. Go Back                            |");
	            System.out.println("\t============================================"); 
	            System.out.println("=".repeat(250));
	            System.out.println("Enter a choice:");
	            
	            try {
	                int roomTypeChoice = Integer.parseInt(reader.readLine());
	                if (roomTypeChoice >= 1 && roomTypeChoice <= 3) {
	                    System.out.println("1. AC\n2. Non AC");
	                    int acChoice = Integer.parseInt(reader.readLine());
	                    
	                    switch (roomTypeChoice) {
	                        case 1:
	                            roomType = (acChoice == 1) ? RoomType.SINGLE_AC : RoomType.SINGLE_NON_AC;
	                            break;
	                        case 2:
	                            roomType = (acChoice == 1) ? RoomType.DOUBLE_AC : RoomType.DOUBLE_NON_AC;
	                            break;
	                        case 3:
	                            roomType = (acChoice == 1) ? RoomType.SUITE_AC : RoomType.SUITE_NON_AC;
	                            break;
	                    }
	                    
	                    validChoice = true;
	                } else if (roomTypeChoice == 4) {
	                    validChoice = false;
	                } else {
	                    System.out.println("Invalid input. Please enter a valid option.");
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a number.");
	            }
	        }
	        
	        displayAndBookRooms(reader, search, roomBooking, roomType);
	    }


	    private void searchByRoomPrice(BufferedReader reader, Search search, RoomBooking roomBooking) throws IOException, SQLException {
	    	System.out.println("======================================\n"
	                + "| Available Prices of Rooms Per Day: |\n"
	                + "| 1. SINGLE AC      = 1200rs         |\n"
	                + "| 2. SINGLE Non-AC  = 1000rs         |\n"
	                + "| 3. DOUBLE AC      = 1700rs         |\n"
	                + "| 4. DOUBLE Non-AC  = 1500rs         |\n"
	                + "| 5. SUITE AC       = 2700rs         |\n"
	                + "| 6. SUITE Non-AC   = 2500rs         |\n"
	                + "======================================\n");

		    	
	        System.out.println("Enter a room price:");
	        int roomPrice = Integer.parseInt(reader.readLine());
	        displayRooms(reader, search, roomBooking, roomPrice);
	    }

	    
	    public void displayRegisteredUserDetails(int customerId) {
	        try {
	            String query = "SELECT c.customer_id, c.first_name, c.last_name, c.gender, c.dob, c.phone, c.email, c.address, c.zip_code, a.user_name FROM DINESH.customer c JOIN DINESH.account a ON c.customer_id = a.customer_id WHERE c.customer_id = ?";
	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setInt(1, customerId);
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                System.out.println("=".repeat(250));
	                System.out.println("-------------------------------------------------------------------------[Registered User Details]--------------------------------------------------------------------");
	                System.out.println("=".repeat(250));
	                System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
	                System.out.println("First Name: " + resultSet.getString("first_name"));
	                System.out.println("Last Name: " + resultSet.getString("last_name"));
	                System.out.println("Gender: " + resultSet.getString("gender"));
	                System.out.println("Date of Birth: " + resultSet.getString("dob"));
	                System.out.println("Phone: " + resultSet.getString("phone"));
	                System.out.println("Email: " + resultSet.getString("email"));
	                System.out.println("Address: " + resultSet.getString("address"));
	                System.out.println("Zip Code: " + resultSet.getString("zip_code"));
	                System.out.println("Username: " + resultSet.getString("user_name"));
	                System.out.println("=".repeat(250));
	            } else {
	                System.out.println("No user found with ID: " + customerId);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }

	    private void adminOperations(BufferedReader reader) {
	        Admin admin = new Admin(conn);
			boolean running = true;
			while (running) {
			    try {
	                System.out.println("=".repeat(250));
	                System.out.println("\t=====================================");
	                System.out.println("\t|          ADMIN OPTIONS            |");
	                System.out.println("\t=====================================");
	                System.out.println("\t|  1. Add Room                      |");
	                System.out.println("\t|  2. Update Room                   |");
	                System.out.println("\t|  3. Delete Room                   |");
	                System.out.println("\t|  4. View Rooms                    |");
	                System.out.println("\t|  5. Go Back                       |");
	                System.out.println("\t|  6. Exit                          |");
	                System.out.println("\t|                                   |");
	                System.out.println("\t=====================================");

			        System.out.print("Enter your choice: ");
			        String input = reader.readLine();
			        int choice;
			        try {
			            choice = Integer.parseInt(input);
			        } catch (NumberFormatException e) {
			            throw new CustomException("Invalid input. Please enter a number.");
			        }
			        switch (choice) {
			            case 1:
			            	displayAll();
			                System.out.println("=".repeat(250));
			                System.out.println("Enter a roomNumber to Add");
			                int num = Integer.parseInt(reader.readLine());
			                System.out.println("\nEnter a roomType");
			                String roomType = reader.readLine();
			                System.out.println("\nEnter a roomPrice");
			                int price = Integer.parseInt(reader.readLine());
			                admin.addRoom(new Room(num, roomType, price));
			                break;
			            case 2:
			            	displayAll();
			                System.out.println("=".repeat(250));
			                admin.updateRoom(reader);
			                break;
			            case 3:
			            	displayAll();
			                System.out.println("=".repeat(250));
			                admin.deleteRoom(reader);
			                break;
			            case 4:
			            	displayAll();
			                System.out.println("=".repeat(250));
			                break;
			            case 5:
			                System.out.println("Exiting...");
			                running = false;
			                break;
			            case 6:
			            	System.out.println("Are You Sure Want To Exit (1.Quit 2.Continue)");
	                    	int i= Integer.parseInt(reader.readLine());
	                    	if(i==1) {
	                    	System.exit(0);}
	                    	else {
	                    		continue;
	                    	}
	                    	break;
			            default:
			                throw new CustomException("Invalid choice. Please try again.");
			        }
			    } catch (CustomException | IOException | SQLException e) {
			        System.out.println(e.getMessage());
			    }
			}
	    }

	    private void displayAll() throws SQLException {
	    	String sql = "SELECT room_number, room_type, rate, available FROM DINESH.rooms WHERE available = 'AVAILABLE'";
	    	try (PreparedStatement statement = conn.prepareStatement(sql);
	    	     ResultSet resultSet = statement.executeQuery()) {
	    	    System.out.println("============================================================================");
	    	    System.out.println("|                     ROOM DETAILS                                          |");
	    	    System.out.println("=============================================================================");
	    	    System.out.println("|  Room Number  |  Room Type            |   Room Price  |  Available Rooms  |");
	    	    while (resultSet.next()) {
	    	        System.out.println("---------------------------------------------------------------");
	    	        System.out.printf("|  %-12s |  %-20s |  %-12.2f |  %-16s |\n",
	    	                resultSet.getString("room_number"),
	    	                resultSet.getString("room_type"),
	    	                resultSet.getDouble("rate"),
	    	                resultSet.getString("available"));
	    	    }
	    	    System.out.println("================================================================");
	    	}
	    	
	    }
	    private void displayAllRooms() throws SQLException {
	        String sql = "SELECT room_type, RATE, COUNT(*) AS available_rooms FROM DINESH.rooms WHERE AVAILABLE = 'AVAILABLE' GROUP BY room_type, RATE";
	        try (PreparedStatement statement = conn.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery()) {
	            System.out.println("===============================================================");
	            System.out.println("|                     ROOM DETAILS                             |");
	            System.out.println("===============================================================");
	            System.out.println("|  Room Type            |   Room Price       |  Available Rooms              |");
	            while (resultSet.next()) {
	            System.out.println("---------------------------------------------------------------");
	                System.out.printf("|  %-20s |  %-15.2f |  %-16d |\n",
	                        resultSet.getString("room_type"),
	                        resultSet.getDouble("RATE"),
	                        resultSet.getInt("available_rooms"));
	            }
	            System.out.println("================================================================");
	        }
	    }

	private void displayAndBookRooms(BufferedReader reader, Search search, RoomBooking roomBooking, RoomType roomType) {
	    try {
	        ResultSet roomSearchResult = search.searchRoomsByType(roomType.name());
	        
	        while (roomSearchResult.next()) {
	            System.out.println("Room Number: " + roomSearchResult.getInt(1) + " Room Type: " + roomSearchResult.getString(2) + "  Room Price: " + roomSearchResult.getDouble(3) + "  Room status: " + roomSearchResult.getString(4));
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}

	private void displayRooms(BufferedReader reader, Search search, RoomBooking roomBooking, int roomPrice) {
	    try {
	        ResultSet roomSearchResult = search.searchRoomsByPrice(roomPrice);
	        while (roomSearchResult.next()) {
	            System.out.println("Room Number: " + roomSearchResult.getInt(1)+"Room Type: " + roomSearchResult.getString(2)+"  Room Price: " + roomSearchResult.getDouble(3)+"  Room status: " + roomSearchResult.getString(4));
	        }
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	}
	private int fetchRoomNumberByType(String roomType) {
		int roomNumber=0;
	    try {
	        String query= "SELECT room_number FROM DINESH.rooms WHERE room_Type = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, roomType); 
	        ResultSet resultset = statement.executeQuery();
	        if(resultset.next()) {
	            return roomNumber = resultset.getInt("room_number");
	        }
	    } catch(SQLException e) {
	        System.out.println(e.getMessage());
	    }
		return roomNumber;
	}
	public static long calculateDaysBetween(String startDate, String endDate) {
	        LocalDate start = LocalDate.parse(startDate);
	        LocalDate end = LocalDate.parse(endDate);
	        
	        return ChronoUnit.DAYS.between(start, end);
	    }
	 private  void setCustomerDetails(Customer cus, BufferedReader reader,int Id) throws IOException {
		    boolean updating = true;
		    while (updating) {
		    	try {
		    	System.out.println("=".repeat(250));	
		    	System.out.println("\t=================================");
		    	System.out.println("\t|         Update Menu            |");
		    	System.out.println("\t=================================");
		    	System.out.println("\t|  1. Update Username               |");
		    	System.out.println("\t|  2. Update email               |");
		    	System.out.println("\t|  3. Update phone number        |");
		    	System.out.println("\t|  4. Update address             |");
		    	System.out.println("\t|  5. Update password            |");
		    	System.out.println("\t|  6. Go back                    |");
		    	System.out.println("\t=================================");

		    	System.out.println("=".repeat(250));	
		        System.out.println("Enter your choice:");
		        int choice = Integer.parseInt(reader.readLine());
            	cus.setCustomerID(Id);
		        switch (choice) {
		        case 1 :
		        	String userName = null;
	                boolean isValidUserName = false;
	                do {
	                    try {
	                        System.out.println("Enter new  like Dineshkumar");
	                        userName = reader.readLine();
	                        if (!validate.isValidFirstName(userName)) {
	                            throw new CustomException("You may have entered an incorrect userName format. Please ensure it follows the correct format.");
	                        } else {
	                            cus.setEmail(userName);
	                            cus.updateName(cus);
	                            isValidUserName = true;
	                        }
	                    } catch (CustomException e) {
	                        System.out.println(e.getMessage());
	                    }
	                } while (!isValidUserName);
	                break;
		            case 2:
		            	String email = null;
		                boolean isValidEmail = false;
		                do {
		                    try {
		                        System.out.println("Enter new  like Dineshkumar@gmail.com:");
		                        email = reader.readLine();
		                        if (!validate.isValidEmail(email)) {
		                            throw new CustomException("You may have entered an incorrect email format. Please ensure it follows the correct format.");
		                        } else {
		                            cus.setEmail(email);
		                            cus.updateEmail(cus);
		                            isValidEmail = true;
		                        }
		                    } catch (CustomException e) {
		                        System.out.println(e.getMessage());
		                    }
		                } while (!isValidEmail);
		                break;
		            case 3:
		            	String phoneNumber = null;
	            	    boolean isValidPhoneNumber = false;
	            	    do {
	            	        try {
	            	            System.out.println("Enter new phone number contains only 10 numbers:");
	            	            phoneNumber = reader.readLine();
	            	            if (!validate.isValidPhoneNumber(phoneNumber)) {
	            	                throw new CustomException("You may have entered an incorrect phone number format. Please ensure it follows the correct format.");
	            	            } else {
	            	                cus.setPhone(phoneNumber);
	            	                cus.updateMobileNumber(cus);
	            	                isValidPhoneNumber = true;
	            	            }
	            	        } catch (CustomException e) {
	            	            System.out.println(e.getMessage());
	            	        }
	            	    } while (!isValidPhoneNumber);
	            	    break;
		            case 4:
		            	  System.out.println("Enter new address:");
			                String address = reader.readLine();
			                cus.setAddress(address);
			                cus.updateAddress(cus);
			                break;

		            case 5:
		            	String Password = null;
	            	    boolean isValidPass = false;
	            	    do {
	            	        try {
	            	            System.out.println("Enter new Password should contain one upperCase one lower case followed by number:");
	            	            Password = reader.readLine();
	            	            if (!validate.isValidPassword(Password)) {
	            	                throw new CustomException("You may have entered an incorrect phone number format. Please ensure it follows the correct format.");
	            	            } else {
	            	                cus.setPhone(Password);
	            	                cus.updateMobileNumber(cus);
	            	                isValidPhoneNumber = true;
	            	            }
	            	        } catch (CustomException e) {
	            	            System.out.println(e.getMessage());
	            	        }
	            	    } while (!isValidPass);
	            	    break;
		            	
		            case 6:
		                updating = false;
		                break;
		            default:
		            	throw new CustomException("you may entered a wrong number please enter a valid input (1,2,3)");
		               
		        }
		    }catch(NumberFormatException e) {
		    	System.out.println("you may entered a wrong number please enter a valid input (1,2,3)");
		    }
		    	catch(CustomException e) {
		    	System.out.println(e.getMessage());
		    }
		    }
		}
	   
	 public void guest(BufferedReader reader) {
		    boolean exit = false;
		    
		    while (!exit) {
		    	try {
		        System.out.println("\t=====================================");
		        System.out.println("\t|         SEARCH OPTIONS             |");
		        System.out.println("\t=====================================");
		        System.out.println("\t|     1. Search                      |");
		        System.out.println("\t|     2. View Feedback               |");
		        System.out.println("\t|     3. Exit                        |");
		        System.out.println("\t|                                    |");
		        System.out.println("\t=====================================");
		        
		        System.out.print("Enter your choice: ");
		        int choice =Integer.parseInt(reader.readLine()) ;
		        
		        switch (choice) {
		            case 1:
		                System.out.println("You selected: Search");
		                Search s= new Room(conn);
		                RoomBooking rb=new RoomBooking(conn);
                        performSearchOptions(reader, s, rb);
		                break;
		            case 2:
		                System.out.println("You selected: View Feedback");
		                feed.fetchFeedback(conn);
		                break;
		            case 3:
		                System.out.println("Exiting...");
		                exit = true;
		                break;
		            default:
		                System.out.println("Invalid choice. Please enter a number from 1 to 3.");
		        }
		    }
		    catch(NumberFormatException |IOException e) {
		    	System.out.println(e.getMessage());
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
			}
		}
      }
	 private void displayReservationDetails(int customerId) throws SQLException {
	        String reservationDetailsSql =" SELECT r.reservation_id, r.room_number, r.reserved_date, r.check_in_date, r.check_out_date, r.status, p.payment_id, p.reservation_id , p.amount, p.payment_date, p.payment_type, p.payment_status FROM DINESH.reservation r JOIN DINESH.payment p ON r.reservation_id = p.reservation_id WHERE r.customer_id = ?";
	        PreparedStatement statement = conn.prepareStatement(reservationDetailsSql);
	        statement.setInt(1, customerId);
	        ResultSet resultSet = statement.executeQuery();
	        
	        while (resultSet.next()) {
	        	System.out.println("\t======================================================================================================================================================");
	        	System.out.println("\t|                        Booking history                                                                                                             |");
	        	System.out.println("\t======================================================================================================================================================");
	        	System.out.println("\t| Reservation ID | Room Number | Check-in Date | Check-out Date | RESERVED_DATE       | Status   | Payment ID | Payment Date | Payment Amount | Payment Type |");
	        	System.out.println("\t======================================================================================================================================================");
	        	while (resultSet.next()) {
	        		
	        	
	        	    System.out.printf("\t| %-15d| %-12d| %-14s| %-15s| %-19s| %-9s| %-11d| %-13s| %-15.2f| %-13s|%n",
	        	        resultSet.getInt("reservation_id"),
	        	        resultSet.getInt("room_number"),
	        	        resultSet.getString("check_in_date"),
	        	        resultSet.getString("check_out_date"),
	        	        resultSet.getString("RESERVED_DATE"),
	        	        resultSet.getString("status"),
	        	        resultSet.getInt("payment_id"),
	        	        resultSet.getDate("payment_date"),
	        	        resultSet.getDouble("amount"),
	        	        resultSet.getString("payment_type"));
	        	}
	        	System.out.println("\t======================================================================================================================================================");

	        }
	    }
	}
	 
	
	 
	    
