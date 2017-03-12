package com.codepath.apps.mysimpletweet.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweet.R;
import com.codepath.apps.mysimpletweet.activity.ProfileActivity;
import com.codepath.apps.mysimpletweet.fragment.ComposeFragment;
import com.codepath.apps.mysimpletweet.fragment.HomeTimeLineFragment;
import com.codepath.apps.mysimpletweet.fragment.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweet.fragment.TweetsListFragment;

import static com.loopj.android.http.AsyncHttpClient.log;

public class TimelineActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("TimeLine");
        String title = actionBar.getTitle().toString();

        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super .onOptionsItemSelected(item);
    }

    public void onProfleView(MenuItem mi){

        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);


    }

    public void edit(MenuItem mi) {
        // handle click here
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment editNameDialogFragment = ComposeFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");



    }



    public class TweetsPagerAdapter extends FragmentPagerAdapter{

        private String tabTitles[]={"Home","Mentions"};

        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
             if(position == 0){
                 return new HomeTimeLineFragment();
             }else if (position == 1){
                 return new MentionsTimelineFragment();
             }else{
                 return null;
             }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

    }





//    @Override
//    public void onComposed(Tweet tweet) {
//        fragmentTweetList.add(tweet);
//    }

}
