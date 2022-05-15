package com.example.miolaproject;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Users implements Serializable {
    // getter method for our id
    public String getId() {
        return id;
    }

    // setter method for our id
    public void setId(String id) {
        this.id = id;
    }
    @Exclude
    private String id;

    private String fullName ;
    private String email ;
    private String semester ;
   private  String role;
    private String phone ;
    private String departement ;
    private String image ;
    private String password ;

    public Users() {
    }

    public Users(String fullName , String email , String semester , String phone , String departement , String image ,String password,String role) {
        this.fullName = fullName;
        this.email = email;
        this.semester = semester;
        this.phone = phone;
        this.departement = departement;
        this.image = image;
        this.password = password;
        this.role=role ;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
