package com.codepath.apps.mysimpletweet.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweet.ComposeDialogListener;
import com.codepath.apps.mysimpletweet.R;
import com.codepath.apps.mysimpletweet.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweet.TwitterClient;
import com.codepath.apps.mysimpletweet.models.Account;
import com.codepath.apps.mysimpletweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;
import static com.codepath.apps.mysimpletweet.TwitterApplication.getRestClient;
import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by sharonyu on 2017/3/5.
 */

public class ComposeFragment extends DialogFragment {

    private EditText etContent;
    private TextView uAccount;
    private TextView uName;
    private Button btn_tweet;
    private ImageView img_profile;
    private TextView tweetCount;
    private TweetsArrayAdapter aTweets;
    private TwitterClient client;
    private OnTweetPostListener listener;
    public interface OnTweetPostListener {
        public void onTweetPost(Tweet tweet);
    }

    public ComposeFragment(){

    }


    public static ComposeFragment newInstance(String title) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    private void populateProfile() {

        client.getUserInfo( new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Account account = Account.fromJson(json);
                uName.setText(account.getName());
                uAccount.setText(account.getScreenName());
                Glide.with(getContext())
                        .load(account.getUserInfo())
                        .into(img_profile);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("debug",errorResponse.toString());
            }
        });



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_edit, container);
        uAccount = (TextView)view.findViewById(R.id.uAccoun);
        uName = (TextView)view.findViewById(R.id.uName);
        img_profile = (ImageView)view.findViewById(R.id.img_porfile);

        tweetCount = (TextView) view.findViewById(R.id.tweetCount);
        etContent = (EditText) view.findViewById(R.id.etContent);
        etContent.setSingleLine(false);
        client = getRestClient();
        etContent.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                tweetCount.setText(String.valueOf(140 - s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        });
        populateProfile();

        btn_tweet = (Button) view.findViewById(R.id.btn_Tweet);
        btn_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTweet();
            }


        });



        return view;
    }






    private void postTweet() {
        final String tweetBody = etContent.getText().toString();
        Log.d("d",tweetBody);

        client.postUpdateStatus(tweetBody, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("d",json.toString());

                Tweet tweet = Tweet.fromJSON(json);
                Bundle b = new Bundle();
                b.putString("tweet",tweet.toString());
                dismiss();



//                listener.onTweetPost(tweet.fromJSON(json));
//                log.d("d",tweet.fromJSON(json).toString());
//                ComposeDialogListener composeDialogListener = (ComposeDialogListener)getActivity();
//                composeDialogListener.onComposed(tweet.fromJSON(json));
                dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                dismiss();
            }
        });



    }





}


