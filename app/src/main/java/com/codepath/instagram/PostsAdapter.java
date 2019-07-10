package com.codepath.instagram;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.codepath.instagram.Models.Post;
import com.parse.ParseFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private SwipeRefreshLayout swipeContainer;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    //creates individual row in recycler view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //puts data in row
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    //returns how many items in dataset
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvCaption;
        private TextView tvHandle2;
        private TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvUser);
            ivImage = itemView.findViewById(R.id.ivPost);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvHandle2 = itemView.findViewById(R.id.tvUser2);
            tvTime = itemView.findViewById(R.id.tvHomeTime);
            //add itemView's OnClickListener
            //itemView.setOnClickListener(PostFragment.class);

        }

        //add in data for specific user's post
        public void bind(Post post) {
            tvHandle.setText(post.getUser().getUsername());
            tvHandle2.setText(post.getUser().getUsername());
            tvTime.setText(getRelativeTimeAgo(String.valueOf(post.getCreatedAt())));
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            tvCaption.setText(post.getCaption());
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    /*@Override
    public void onClick(View v) {
        // get item position
        int position = getAdapterPosition();
        // make sure the position exists in the view
        if (position != RecyclerView.NO_POSITION) {
            // get the movie at the position, this won't work if the class is static
            Post post = posts.get(position);
            // create intent for the new activity
            Intent intent = new Intent(context, PostDetails.class);
            //serialize the movie using parceler, use its short name as a key
            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(post));
            // show the activity
            context.startActivity(intent);
        }
    }*/

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
