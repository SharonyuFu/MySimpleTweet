package com.codepath.apps.mysimpletweet;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweet.fragment.ComposeFragment;
import com.codepath.apps.mysimpletweet.models.Tweet;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.codepath.apps.mysimpletweet.TwitterApplication.getRestClient;
import static com.codepath.apps.mysimpletweet.models.SampleModel_Table.id;
import static com.loopj.android.http.AsyncHttpClient.log;
import static java.util.Collections.addAll;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogListener{

    private TwitterClient client;

    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet>tweets;
    private ListView lvTweets;

    public TweetsArrayAdapter getaTweets() {
        return aTweets;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = getRestClient();
        populateTimeline();

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("TimeLine");
        String title = actionBar.getTitle().toString();

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this,tweets);
        lvTweets.setAdapter(aTweets);



        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.

            }
        });



    }

    private void loadNextDataFromApi(long maxId) {

        Tweet tweet = aTweets.getItem(aTweets.getCount()-1);
        maxId = tweet.getUId()-1;

        client.getHomeTimeline(maxId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                aTweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("debug",errorResponse.toString());
            }

        });



    }



    private void populateTimeline() {

        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("debug",json.toString());
                aTweets.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
               Log.d("debug",errorResponse.toString());
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }
        return super .onOptionsItemSelected(item);
    }

    public void edit(MenuItem mi) {
        // handle click here
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment editNameDialogFragment = ComposeFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");



    }




    @Override
    public void onComposed(Tweet tweet) {
        aTweets.add(tweet);
    }
}
