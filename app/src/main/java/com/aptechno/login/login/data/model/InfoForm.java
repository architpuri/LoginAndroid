package com.aptechno.login.login.data.model;

public class InfoForm {
    private String username;
    private String name;
    private String mobileNumber;

    protected InfoForm() {}


    public InfoForm(String username, String name, String mobileNumber) {
        super();
        this.username = username;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    @Override
    public String toString() {
        return "InfoForm [username=" + username + ", name=" + name + ", mobileNumber="
                + mobileNumber + "]";
    }

}
