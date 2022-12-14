package com.utsuru.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Anime implements Serializable {

    @Id
    private int IDA;

    @OneToMany(mappedBy = "anime")
    private List<UserReview> UR;
    private String Name;
    private int Episode;
    private String Imgurl;

    public Anime() {
    }

    public Anime(int iDA, int Episode, String name, String imgurl) {
        this.Episode = Episode;
        IDA = iDA;
        Name = name;
        Imgurl = imgurl;
    }

    public int getIDA() {
        return IDA;
    }

    public int getEpisode() {
        return Episode;
    }

    public void setEpisode(int episode) {
        Episode = episode;
    }

    @Override
    public String toString() {
        return "Anime [Episode=" + Episode + ", IDA=" + IDA + ", Imgurl=" + Imgurl + ", Name=" + Name + "]";
    }

    public void setIDA(int iDA) {
        IDA = iDA;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }

    public List<UserReview> getUR() {
        return UR;
    }

    public void setUR(List<UserReview> uR) {
        UR = uR;
    }

}
