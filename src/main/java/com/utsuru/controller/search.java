package com.utsuru.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

import org.json.JSONException;
import org.json.JSONObject;

@Named
@RequestScoped
public class search implements Serializable {

    private String query;
    private int last_page;

    public List<Object> getAniList(String q, String p) throws MalformedURLException, IOException, JSONException {
        // Validator
        // If no page / page is null
        if ((!(Integer.parseInt(p) >= 1)) || (q == null)) {
            return null;
        }
        // Remove Space
        q = q.replaceAll(" ", "%20");
        //
        JSONObject arr = new JSONObject(getRawJikan(q, p));
        last_page = arr.getInt("last_page");
        return arr.getJSONArray("results").toList();

    }

    private String getRawJikan(String q, String p) throws MalformedURLException, IOException {

        String line;
        StringBuffer responseContent = new StringBuffer();
        HttpURLConnection conn = (HttpURLConnection) new URL(
                "https://api.jikan.moe/v3/search/anime?q=" + q + "&page=" + p + "&limit=49").openConnection();
        for (int i = 0; i < 2; i++) {

            try {

                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3300);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if (conn.getResponseCode() > 299) {
                    reader.close();
                } else {
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();
                    return responseContent.toString();
                }

            } catch (java.net.SocketTimeoutException e) {
                continue;
            }
        }
        return null;
    }

    public int getLast_page() {
        return last_page;
    }

    public String go() throws IOException {

        return "/search.xhtml?q=" + query + "&page=1faces-redirect=true";

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

}
