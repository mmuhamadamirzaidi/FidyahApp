package com.example.user.fidyahapp.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AdminDetails {

    public String adminPassword;
    public String adminUsername;

    public AdminDetails() {
    }

    public AdminDetails(String adminPassword, String adminUsername) {
        this.adminPassword = adminPassword;
        this.adminUsername = adminUsername;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getAdminUsername() {
        return adminUsername;
    }
}
