package com.persondetaills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    // Existing methods...

    private Connection connection;

    public InputValidator(Connection connection) {
        this.connection = connection;
    }

    public boolean isValidFirstName(String firstName) {
        return firstName.matches("[a-zA-Z]+$");
    }

    public boolean isValidLastName(String lastName) {
        return lastName.matches("[a-zA-Z]+$");
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[6-9]\\d{9}");
    }

    public boolean isValidDateOfBirth(String dob) {
        return dob.matches("^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$") && Agecalculation(dob);
    }

    public boolean isValidZipCode(String zipCode) {
        return zipCode.matches("\\d{3}(-\\d{3})?");
    }

    public boolean isValidUsername(String username) {
        return username.matches("^(?=.*[a-z])(?=.*\\d).+$");
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public boolean isvalidCheckDate(String checkDate) {
        return checkDate.matches("^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$");
    }

    public boolean Agecalculation(String dob) {
        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        return age >= 20;
    }

    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle this properly in your application
        }
        return false;
    }
}
