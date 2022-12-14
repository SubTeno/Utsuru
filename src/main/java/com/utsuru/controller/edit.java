package com.utsuru.controller;

import org.hibernate.cfg.Configuration;

import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.utsuru.model.Anime;
import com.utsuru.model.UserReview;

import org.hibernate.Session;

import javax.faces.view.ViewScoped;

import java.io.Serializable;

@Named
@ViewScoped
@URLBeanName(value = "edit")
@URLMapping(id = "edit", pattern = "/home/user/edit/#{edit.ID}", viewId = "/edit.xhtml")
public class edit implements Serializable {

    @Inject
    private app app;
    @Inject
    private anime animepage;
    private Integer ID;
    private Integer Score;
    private Integer Progress;
    private String Status;
    private Anime anime;
    private Integer UID;
    private Session s = new Configuration().configure().buildSessionFactory().openSession();

    @URLAction(mappingId = "edit")
    public void init() {

        app.getUser().getUR().forEach(obj -> {
            if (obj.getAnime().getIDA() == ID) {
                UID = obj.getID();
                Progress = obj.getProgress();
                Score = obj.getScore();
                Status = obj.getStatus();
                anime = obj.getAnime();
            }
        });
    }

    public void add() {
        s.beginTransaction();
        UserReview nUserReview = new UserReview(0, 0, "Watching");
        Anime obj = (Anime) s.createQuery("from Anime where IDA = :ID")
                .setParameter("ID", Integer.parseInt(animepage.getAniID())).uniqueResult();
        if (obj != null) {
            nUserReview.setAnime(obj);
        } else {
            obj = new Anime(Integer.parseInt(animepage.getAniID()), Integer.parseInt(animepage.getEps()),
                    animepage.getTitle_eng(), animepage.getImg_url());
            s.save(obj);
        }
        nUserReview.setAnime(obj);
        nUserReview.setUser(app.getUser());
        s.save(nUserReview);
        s.getTransaction().commit();
        s.evict(nUserReview);
    }

    public void delete(Integer ID) {

        UserReview newRow = (UserReview) s.get(UserReview.class, ID);
        s.beginTransaction();
            s.delete(newRow);
            s.getTransaction().commit();
        s.evict(newRow);
    }

    public void update(Integer ID) {

        UserReview newRow = (UserReview) s.get(UserReview.class, ID);
        s.beginTransaction();
            newRow.setAnime(anime);
            newRow.setProgress(Progress);
            newRow.setScore(Score);
            newRow.setStatus(Status);
            s.update(newRow);
        s.getTransaction().commit();
        s.evict(newRow);
    }

    public Integer getUID() {
        return UID;
    }

    public void setUID(Integer uID) {
        UID = uID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getScore() {
        return Score;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    public Integer getProgress() {
        return Progress;
    }

    public void setProgress(Integer progress) {
        Progress = progress;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

}
