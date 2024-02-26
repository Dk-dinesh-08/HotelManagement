package com.persondetaills;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
/**
 * Utility class for validating various input data commonly used in a hotel management system.
 * 
 * This class provides methods to validate input data such as first name, last name, age, email, phone number,
 * date of birth, zip code, username, and password according to specified rules and patterns.
 * 
 * @author Dinesh Karmegam(expleo)
 * @since 22 Feb 2024
 */
public class InputValidator {
    public  boolean isValidFirstName(String firstName) {
    	//@return true or false
        return firstName.matches("[a-zA-Z]+$");
    }

    public  boolean isValidLastName(String lastName) {
    	//@return true or false
        return lastName.matches("[a-zA-Z]+$");
    }

 
    public  boolean isValidEmail(String email) {
    	//@return true or false
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public  boolean isValidPhoneNumber(String phoneNumber) {
    	//@return true or false
    	return phoneNumber.matches("[6-9]\\d{9}");
    }

    public boolean isValidDateOfBirth(String dob) {
    	//@return true or false
        return dob.matches("^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$")&&  Agecalculation(dob) ;

    }
    public boolean isValidZipCode(String zipCode) {
    	//@return true or false
        return zipCode.matches("\\d{3}(-\\d{3})?");
    }

    public  boolean isValidUsername(String username) {
    	//@return true or false
        return username.matches("^(?=.*[a-z])(?=.*\\d).+$");
    }

    public  boolean isValidPassword(String password) {
    	//@return true or false
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
    
    public boolean isvalidCheckDate(String checkDate) {
    	//@return true or false
        return checkDate.matches("^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$");
    }
    public boolean  Agecalculation(String dob){
    	LocalDate birthDate = LocalDate.parse(dob);
    	 LocalDate currentDate = LocalDate.now();
    	    int age = Period.between(birthDate, currentDate).getYears();

    	    return age >= 20;
    }

   
}
