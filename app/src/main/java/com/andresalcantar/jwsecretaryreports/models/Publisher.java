package com.andresalcantar.jwsecretaryreports.models;

/**
 * Created by andres.alcantar on 23/12/2016.
 */

public class Publisher {
    private String name;
    private String phonenumber;
    private String email;
    private ServiceGroup serviceGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }
}
