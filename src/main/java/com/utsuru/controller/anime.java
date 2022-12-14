package com.utsuru.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.utsuru.model.UserReview;

@Named
@ViewScoped
@URLBeanName(value = "anime")
@URLMapping(id = "anime", pattern = "/home/anime/#{anime.aniID}", viewId = "/anime.xhtml")
public class anime implements Serializable {

    private String aniID;
    private String title_eng;
    private String desc;
    private String img_url;
    private String title_jp;
    private String trailer_url;
    private String eps;
    private String score;
    private String rank;
    private String status;
    private String rating;
    @Inject
    private app currentUser;
    private UserReview currentUserReview;
    private Session s = new Configuration().configure().buildSessionFactory().openSession();

    @URLAction(mappingId = "anime")
    public void init() throws MalformedURLException, IOException {

        String line;
        JSONObject arr = null;
        StringBuffer responseContent = new StringBuffer();
        HttpURLConnection conn = (HttpURLConnection) new URL("https://api.jikan.moe/v3/anime/" + aniID + "")
                .openConnection();
        conn.setConnectTimeout(13000);
        conn.setReadTimeout(15000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        if (conn.getResponseCode() > 299) {
            reader.close();
        } else {
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        arr = new JSONObject(responseContent.toString());
        Map<String, Object> ma = arr.toMap();
        ma.forEach((key, val) -> {
            if (val == null) {
                ma.replace(key, "-");
            }
        });

        //
        title_eng = ma.get("title").toString();
        title_jp = ma.get("title_japanese").toString();
        desc = ma.get("synopsis").toString();
        img_url = ma.get("image_url").toString();
        trailer_url = ma.get("trailer_url").toString();
        status = ma.get("status").toString();
        rating = ma.get("rating").toString();
        eps = ma.get("episodes").toString();
        score = ma.get("score").toString();
        rank = ma.get("rank").toString();
        if (trailer_url == "-") {
            trailer_url = "";
        }
        //
    }

    public Integer ifFavourite(String ID) {
        if (currentUser.getUser() == null) {
            return 1;
        }
        s.beginTransaction();
        UserReview currentUserReview = (UserReview) s
                .createQuery("from UserReview where anime_IDA = :ID and user_ID = :UID")
                .setParameter("ID", Integer.parseInt(ID)).setParameter("UID", currentUser.getUser().getUID())
                .uniqueResult();
        s.getTransaction().commit();
        if (currentUserReview != null) {
            this.currentUserReview = currentUserReview;
            return 2;
        }
        return 3;
    }

    public UserReview getCurrentUserReview() {
        return currentUserReview;
    }

    public void setCurrentUserReview(UserReview currentUserReview) {
        this.currentUserReview = currentUserReview;
    }

    public String getRank() {
        return rank;
    }

    public String getStatus() {
        return status;
    }

    public String getRating() {
        return rating;
    }

    public void setAniID(String aniID) {
        this.aniID = aniID;
    }

    public String getAniID() {
        return aniID;
    }

    public String getTitle_eng() {
        return title_eng;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getTitle_jp() {
        return title_jp;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public String getEps() {
        return eps;
    }

    public String getScore() {
        return score;
    }

}
