package com.codepath.apps.mysimpletweet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweet.EndlessScrollListener;
import com.codepath.apps.mysimpletweet.R;
import com.codepath.apps.mysimpletweet.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweet.TwitterApplication;
import com.codepath.apps.mysimpletweet.TwitterClient;
import com.codepath.apps.mysimpletweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sharonyu on 2017/3/9.
 */

public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private TwitterClient client;
    protected ImageClickListener imageListener=null;

    public TweetsArrayAdapter getaTweets() {
        return aTweets;
    }
    // inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list,parent,false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadNextDataFromApi(page);
                return true;

            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if( activity instanceof ImageClickListener ){
            imageListener = (ImageClickListener) activity;
        }
    }


    //create lifecycle event

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(),tweets,imageListener);
        client = TwitterApplication.getRestClient();

    }



    private void loadNextDataFromApi(long maxId) {

        Tweet tweet = aTweets.getItem(aTweets.getCount() - 1);
        maxId = tweet.getUId() - 1;

        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
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



    public void addAll(List<Tweet>tweets){
        aTweets.addAll(tweets);

    }

    public interface ImageClickListener{
        public void onImageClick( String screenName );
    }


//    public  void  notifyDataSetChanged(){
//        aTweets.notifyDataSetChanged();
//    }
}
