package com.auto.autoparts_fyp_final;

public class CustomerData {
    public String cfname,clname,email,phone,password;

    public String getCfname() {
        return cfname;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public String getClname() {
        return clname;
    }

    public void setClname(String clname) {
        this.clname = clname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CustomerData()
    {

    }

    public CustomerData(String cfname,String clname, String email, String phone, String password) {
        this.cfname = cfname;
        this.clname = clname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
