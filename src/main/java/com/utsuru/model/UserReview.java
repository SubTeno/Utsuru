package com.utsuru.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserReview implements Serializable {
    @GeneratedValue
    @Id
    private int ID;

    @ManyToOne
    private User user;

    @ManyToOne
    private Anime anime;

    private int Score;

    private int Progress;

    private String status;

    public UserReview() {
    }

    public UserReview(int score, int progress, String status) {
        Score = score;
        Progress = progress;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    @Override
    public String toString() {
        return "UserReview [ID=" + ID + ", Progress=" + Progress + ", Score=" + Score + ", anime=" + anime + ", status="
                + status + ", user=" + user + "]";
    }

    public User getUser() {
        return user;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
