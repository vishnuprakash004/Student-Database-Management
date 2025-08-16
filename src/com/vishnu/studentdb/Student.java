package com.vishnu.studentdb;

import java.time.LocalDate;

public class Student {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String gender;        
    private String contactNumber;
    private String email;

    public Student(Integer studentId, String firstName, String lastName,
                   LocalDate dob, String gender, String contactNumber, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
    }
    public Student(String firstName, String lastName,
                   LocalDate dob, String gender, String contactNumber, String email) {
        this(null, firstName, lastName, dob, gender, contactNumber, email);
    }

    public Integer getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDob() { return dob; }
    public String getGender() { return gender; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }

    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setGender(String gender) { this.gender = gender; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setEmail(String email) { this.email = email; }
}
