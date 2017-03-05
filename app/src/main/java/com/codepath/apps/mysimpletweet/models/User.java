package com.codepath.apps.mysimpletweet.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sharonyu on 2017/3/3.
 */

public class User {
    private String name;
    private Long uid;
    private String screenName;
    private String profilImgUrl;

    public String getName() {
        return name;
    }

    public String getProfilImgUrl() {
        return profilImgUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public Long getUid() {
        return uid;
    }

    public static User fromJson(JSONObject jsonObject){
        User u = new User();

        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profilImgUrl= jsonObject.getString("profile_image_url");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;

    }
}
