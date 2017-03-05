package com.codepath.apps.mysimpletweet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweet.R;
import com.codepath.apps.mysimpletweet.TimelineActivity;
import com.codepath.apps.mysimpletweet.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweet.TwitterClient;
import com.codepath.apps.mysimpletweet.models.Tweet;
import com.codepath.apps.mysimpletweet.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.mysimpletweet.R.id.ivProfileImage;
import static com.codepath.apps.mysimpletweet.R.string.tweet;
import static com.codepath.apps.mysimpletweet.TwitterApplication.getRestClient;
import static com.codepath.apps.mysimpletweet.models.SampleModel_Table.name;
import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by sharonyu on 2017/3/5.
 */

public class ComposeFragment extends DialogFragment{

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }


    private EditText etContent;
    private TextView uAccount;
    private TextView uName;
    private Button btn_tweet;
    private ImageView img_profile;
    private TextView tweetCount;
    private TweetsArrayAdapter aTweets;
    private TwitterClient client;

    public ComposeFragment(){

    }

    public static ComposeFragment newInstance(String title) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
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

        btn_tweet = (Button) view.findViewById(R.id.btn_Tweet);

//        btn_tweet.setOnEditorActionListener(this);

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
        client.postUpdateStatus(tweetBody, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });



    }






}


