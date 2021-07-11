package com.example.covid_hospital;

public class Profile {
    private String name;
    private String status;
    private String profilePic;
    private String email;

    public Profile(){

    }

    public Profile(String name, String status,  String email) {
        this.name = name;
        this.status = status;
        this.profilePic = profilePic;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
