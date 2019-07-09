package com.codepath.instagram.Activities;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.instagram.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostDetails extends AppCompatActivity {

    private TextView tvHandle;
    private ImageView ivImage;
    private TextView tvTime;
    private TextView tvCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        tvHandle = (TextView) findViewById(R.id.tvDetailsHandle);
        tvTime = (TextView) findViewById(R.id.tvDetailsTime);
        tvCaption = (TextView) findViewById(R.id.tvDetailsCaption);
        //TODO add picture
        //tvTime.setText(getRelativeTimeAgo());
    }

    // return how long ago relative to current time tweet was sent
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
