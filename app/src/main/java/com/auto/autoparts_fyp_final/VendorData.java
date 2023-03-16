package com.auto.autoparts_fyp_final;

public class VendorData {
    public String vsname,vname,vphone,email,password,verified,status;



    public VendorData()
    {

    }

    public VendorData(String vsname, String vname , String vphone , String email, String password,String verified,String status) {
        this.vsname = vsname;
        this.vname = vname;
        this.vphone = vphone;
        this.email = email;
        this.password = password;
        this.verified=verified;
        this.status=status;



    }
}
