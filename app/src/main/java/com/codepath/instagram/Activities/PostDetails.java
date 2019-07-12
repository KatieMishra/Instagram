package com.codepath.instagram.Activities;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.instagram.Models.Post;
import com.codepath.instagram.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* Katie Mishra - FBU 2019 - krmishra@stanford.edu
   PostDetails displays a single post in a separate activity,
   with all the same details as item_post.
 */
public class PostDetails extends AppCompatActivity {

    private TextView tvHandle;
    private TextView tvTime;
    private TextView tvCaption;
    private TextView tvHandle2;
    private Post post;
    private ImageView image;
    private TextView tvDetailsNumLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.nav_logo_whiteout);
        getSupportActionBar().setTitle("");

        //unwrap the post passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvHandle = (TextView) findViewById(R.id.tvDetailsHandle);
        tvHandle2 = (TextView) findViewById(R.id.tvDetailsHandle2);
        tvTime = (TextView) findViewById(R.id.tvDetailsTime);
        tvCaption = (TextView) findViewById(R.id.tvDetailsCaption);
        image = (ImageView) findViewById(R.id.ivDetailsImage);
        tvDetailsNumLikes = (TextView) findViewById(R.id.tvDetailsNumLikes);

        ParseFile imageToLoad = post.getImage();
        tvHandle.setText(post.getUser().getUsername());
        tvCaption.setText(post.getCaption());
        tvTime.setText(getRelativeTimeAgo(String.valueOf(post.getCreatedAt())));
        tvHandle2.setText(post.getUser().getUsername());
        tvDetailsNumLikes.setText(Integer.toString(post.getNumLikes()));
        Glide.with(this).load(imageToLoad.getUrl()).into(image);
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
