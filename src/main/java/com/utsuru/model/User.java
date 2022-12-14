package com.utsuru.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User implements Serializable {
    @GeneratedValue
    @Id
    private int ID;
    private String name;
    private String pass;
    @OneToMany(mappedBy = "user")
    private List<UserReview> UR;
    

    
    public User() {
    
    }
    
    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }






    public List<UserReview> getUR() {
        return UR;
    }
    public void setUR(List<UserReview> UR) {
        this.UR = UR;
    }
    public int getUID() {
        return ID;
    }
    public void setUID(int uID) {
        ID = uID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    @Override
    public String toString() {
        return "User [ID=" + ID + ", name=" + name + ", pass=" + pass + "]";
    }

}
