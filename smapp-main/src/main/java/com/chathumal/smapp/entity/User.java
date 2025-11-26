package com.chathumal.smapp.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String password;
    private boolean fulacs;

    public User() {
    }

    public User(int id, String name, String address, String contact, String email, String password, boolean fulacs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.fulacs = fulacs;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFulacs() {
        return fulacs;
    }

    public void setFulacs(boolean fulacs) {
        this.fulacs = fulacs;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fulacs=" + fulacs +
                '}';
    }
}
