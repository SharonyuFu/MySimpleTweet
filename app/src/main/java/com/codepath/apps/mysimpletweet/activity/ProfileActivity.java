package com.codepath.apps.mysimpletweet.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweet.R;
import com.codepath.apps.mysimpletweet.TwitterApplication;
import com.codepath.apps.mysimpletweet.TwitterClient;
import com.codepath.apps.mysimpletweet.fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweet.fragment.UserTimelineFragment;
import com.codepath.apps.mysimpletweet.models.Tweet;
import com.codepath.apps.mysimpletweet.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.os.Build.ID;
import static com.codepath.apps.mysimpletweet.R.string.account;
import static com.loopj.android.http.AsyncHttpClient.log;
import static com.raizlabs.android.dbflow.config.FlowLog.Level.I;


public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();


        String screenName = getIntent().getStringExtra("screen_name");



        if(savedInstanceState == null){
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer,UserTimelineFragment.newInstance(screenName));
            ft.commit();
        }

        getSupportActionBar().setTitle("@" + screenName);

        if (screenName == null){
            client.getUserInfo(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    user = User.fromJson(response);

                    populateProfileHeader(user);
                }
            });

        }else{
            client.getUserInfoTwo(screenName,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    log.d("d",response.toString());
                    user = User.fromJson(response);
                    populateProfileHeader(user);
                }
            });

        }

    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView)findViewById(R.id.tvName);
        TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvTagline.setText(user.getTagLine());
        tvFollowers.setText(user.getFollowersCount()+"Followers");
        tvFollowing.setText(user.getFollowingsCount()+"Following");
        Glide.with(this)
                .load(user.getProfilImgUrl())
                .into(ivProfileImage);


    }
}
