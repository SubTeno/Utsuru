package com.utsuru.controller;

import org.hibernate.cfg.Configuration;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.io.IOException;
import java.io.Serializable;

import com.utsuru.model.*;
import org.hibernate.*;

@Named
@SessionScoped
public class app implements Serializable {

    private User user;
    private String name;
    private String pass;
    private SessionFactory sf = new Configuration().configure().buildSessionFactory();

    public String login() throws IOException, InterruptedException {
        getUser();
        return "/?faces-redirect=true";
    }

    public String logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/home?faces-redirect=true";
    }

    public void register() throws IOException {
        Session s = sf.openSession();
        User newuser = new User(name, pass);
        s.beginTransaction();
        s.save(newuser);
        s.getTransaction().commit();
    }

    public User getUser() {
        Session s = sf.openSession();
        s.beginTransaction();
        User l = (User) s.createQuery("from User where name = :a and pass = :b").setParameter("a", name)
                .setParameter("b", pass).uniqueResult();
        if (l != null) {
            user = l;
            user.setUR(l.getUR());
        }
        s.getTransaction().commit();
        return user;
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

}
