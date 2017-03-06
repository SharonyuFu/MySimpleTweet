package com.codepath.apps.mysimpletweet.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sharonyu on 2017/3/6.
 */

public class Account {

    private String name;

    public String getScreenName() {
        return screenName;
    }

    private String screenName;

    private String profilImgUrl;

    public String getName() {
        return name;
    }

    public String getUserInfo() {
        return profilImgUrl;
    }


    public static Account fromJson(JSONObject jsonObject){
        Account account = new Account();

        try {
            account.name = jsonObject.getString("name");
            account.screenName = jsonObject.getString("screen_name");
            account.profilImgUrl= jsonObject.getString("profile_image_url");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return account;

    }
}
