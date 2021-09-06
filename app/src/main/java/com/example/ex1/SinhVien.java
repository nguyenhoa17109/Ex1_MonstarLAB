package com.example.ex1;

public class SinhVien {
    private int id;
    private String name;
    private String dateOfBirth;
    private String sdt;
    private String major;
    private String edu;

    public SinhVien() {
    }

    public SinhVien(String name, String dateOfBirth, String sdt, String major, String edu) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sdt = sdt;
        this.major = major;
        this.edu = edu;
    }

    public SinhVien(int id, String name, String dateOfBirth, String sdt, String major, String edu) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sdt = sdt;
        this.major = major;
        this.edu = edu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }
}
