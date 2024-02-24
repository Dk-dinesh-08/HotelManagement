package com.persondetaills;

import java.sql.Connection;

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

    public Person(Connection conn)
    {
    	this.conn=conn;
    }
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

    public int personID() {
    	return personID;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void viewProfile() {
    }
   
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getUserName() {
		return account.getUserName();
	}
	public String getPassword() {
		return account.getPassword();
	}

}
