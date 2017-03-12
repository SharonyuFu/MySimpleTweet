package com.codepath.apps.mysimpletweet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweet.activity.ProfileActivity;
import com.codepath.apps.mysimpletweet.fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweet.models.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.codepath.apps.mysimpletweet.R.id.ivProfileImage;
import static com.codepath.apps.mysimpletweet.R.id.tvBody;
import static com.codepath.apps.mysimpletweet.R.id.tvUserName;
import static com.codepath.apps.mysimpletweet.R.string.tweet;

/**
 * Created by sharonyu on 2017/3/3.
 */

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private TweetsListFragment.ImageClickListener imgClickListener=null;




//    public TweetsArrayAdapter(Context context, List <Tweet> tweets){
//        super(context,android.R.layout.activity_list_item,tweets);
//    }
    public TweetsArrayAdapter(FragmentActivity activity, List<Tweet> tweets, TweetsListFragment.ImageClickListener imageListener) {
        super(activity, 0, tweets);
        imgClickListener = imageListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        }

        ViewHolder holder=null;
        if( convertView.getTag() == null ){
            holder = new ViewHolder();
            holder.ivProfileImage = (ImageView) convertView.findViewById(ivProfileImage);
            holder.tvUserName = (TextView) convertView.findViewById(tvUserName);
            holder.tvBody = (TextView) convertView.findViewById(tvBody);
            holder.time = (TextView) convertView.findViewById(R.id.timw);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }




        holder.tvUserName.setText(tweet.getUser().getScreenName());
        holder.tvBody.setText(tweet.getBody());
        holder.time.setText(tweet.getRelativeTimeAgo(tweet.getCreatedAt()));


        holder.ivProfileImage.setImageResource(android.R.color.transparent);
        Glide.with(getContext())
                .load(tweet.getUser().getProfilImgUrl())
                .into(holder.ivProfileImage);


        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                getContext().startActivity(i);
            }
        });



        return convertView;
    }

    public class ViewHolder{
        ImageView ivProfileImage;
        TextView tvUserName;
        TextView tvBody;
        TextView time;

    }

}
