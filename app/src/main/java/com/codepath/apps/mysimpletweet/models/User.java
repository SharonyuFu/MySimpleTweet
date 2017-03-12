package com.codepath.apps.mysimpletweet.models;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.utils.StreamUtils;

import java.util.List;

/**
 * Created by sharonyu on 2017/3/3.
 */

public class User {
    private String name;
    private Long uid;
    private String screenName;
    private String profilImgUrl;
    private String tagLine;
    private int followersCount;
    private int followingsCount;

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

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public static User fromJson(JSONObject jsonObject){
        User u = new User();

        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profilImgUrl= jsonObject.getString("profile_image_url");
            u.tagLine = jsonObject.getString("description");
            u.followersCount= jsonObject.getInt("followers_count");
            u.followingsCount= jsonObject.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;

    }


    public int getFollowersCount() {
        return followersCount;
    }
}
