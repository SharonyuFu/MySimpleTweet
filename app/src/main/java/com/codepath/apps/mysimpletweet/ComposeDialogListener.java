package com.codepath.apps.mysimpletweet;

import com.codepath.apps.mysimpletweet.models.Tweet;

/**
 * Created by sharonyu on 2017/3/6.
 */
public interface ComposeDialogListener {
    void onComposed(Tweet tweet);

}
