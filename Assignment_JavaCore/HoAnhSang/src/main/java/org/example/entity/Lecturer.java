package org.example.entity;

public class Lecturer {
    private int lecturerId;
    private String lecturerFullName;
    private String lecturerEmail;
    private String lecturerDepartment;

    public int getLecturerId() {
        return lecturerId;
    }

    public String getLecturerFullName() {
        return lecturerFullName;
    }

    public void setLecturerFullName(String lecturerFullName) {
        this.lecturerFullName = lecturerFullName;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public String getLecturerDepartment() {
        return lecturerDepartment;
    }

    public void setLecturerDepartment(String lecturerDepartment) {
        this.lecturerDepartment = lecturerDepartment;
    }


}
