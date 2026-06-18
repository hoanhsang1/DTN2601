package org.example.entity;

import java.util.Date;

public class Student {
    private int studentId;
    private String studentFullName;
    private String studentEmail;
    private Date studentBirthday;
    private Major major;

    public int getStudentId() {
        return studentId;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Date getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(Date studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Student(int studentId, String studentFullName, String studentEmail, Date studentBirthday, Major major) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.studentEmail = studentEmail;
        this.studentBirthday = studentBirthday;
        this.major = major;
    }
}
