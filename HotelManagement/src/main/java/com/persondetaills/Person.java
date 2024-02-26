package com.persondetaills;

import java.sql.Connection;
/**
 * The Person class represents a person with personal details.
 */
public class Person {
	int personID =100;
	
	protected Connection conn;
    private String firstName;
    private String lastName;
    private int age;
    private Account account;
    private String gender;
    private String dob;
    private String email;
    private String phone;
    private String address;
    private String zipCode;
    /**
     * Constructs a Person object with the provided database connection.
     * @param conn The database connection
     */
    public Person(Connection conn)
    {
    	this.conn=conn;
    }
    /**
     * Constructs a Person object with the provided personal details.
     * @param firstName The first name of the person
     * @param lastName The last name of the person
     * @param age The age of the person
     * @param gender The gender of the person
     * @param dob The date of birth of the person
     * @param phone The phone number of the person
     * @param email The email address of the person
     * @param address The address of the person
     * @param zipCode The ZIP code of the person's address
     * @param account The account associated with the person
     */
    public Person( String firstName, String lastName,int age,String gender,String dob, String phone,String email, String address,String zipCode,Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age=age;
        this.gender=gender;
        this.email = email;
        this.dob=dob;
        this.phone = phone;
        this.address = address;
        this.zipCode=zipCode;
		this.account = account;
    }
    /**
     * Returns the person's ID.
     * @return The person's ID
     */
    public int personID() {
    	return personID;
    }
    /**
     * Returns the first name of the person.
     * @return The first name of the person
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Sets the first name of the person.
     * @param firstName The first name of the person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Returns the last name of the person.
     * @return The last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     * @param lastName The last name of the person
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the email address of the person.
     * @return The email address of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the person.
     * @param email The email address of the person
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the person.
     * @return The phone number of the person
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the person.
     * @param phone The phone number of the person
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the address of the person.
     * @return The address of the person
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the person.
     * @param address The address of the person
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Views the profile of the person.
     */
    public void viewProfile() {
        // Method implementation...
    }

    /**
     * Returns the gender of the person.
     * @return The gender of the person
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the person.
     * @param gender The gender of the person
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the date of birth of the person.
     * @return The date of birth of the person
     */
    public String getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the person.
     * @param dob The date of birth of the person
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * Returns the ZIP code of the person's address.
     * @return The ZIP code of the person's address
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the ZIP code of the person's address.
     * @param zipCode The ZIP code of the person's address
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Returns the age of the person.
     * @return The age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the person.
     * @param age The age of the person
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the username associated with the person's account.
     * @return The username associated with the person's account
     */
    public String getUserName() {
        return account.getUserName();
    }

    /**
     * Returns the password associated with the person's account.
     * @return The password associated with the person's account
     */
    public String getPassword() {
        return account.getPassword();
    }

}
