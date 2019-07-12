package com.codepath.instagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/* Katie Mishra - FBU 2019 - krmishra@stanford.edu
   Post defines the elements of a post and getters and setters for each item.
 */
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_CAPTION = "caption";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_NUM_COMMENTS = "numComments";
    //public static final JSONArray KEY_NUM_LIKED_BY = "likedBy";
    public JSONArray userLikes() {
        return getJSONArray("likedBy");
    }

    public void likePost(ParseUser u) {
        add("likedBy", u);
    }

    public void unlikePost(ParseUser currentUser) {
        ArrayList<ParseUser> users = new ArrayList<>();
        users.add(currentUser);
        removeAll("likedBy", users);
    }

    public String getCaption() {
        return getString(KEY_CAPTION);
    }

    public void setCaption(String caption) {
        put(KEY_CAPTION, caption);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public int getNumLikes() {
        if (userLikes() == null) return 0;
        return userLikes().length();
    }

    public boolean isLiked() {
        JSONArray a = userLikes();
        if (a != null) {
            for (int i = 0; i < a.length(); i++) {
                try {
                    a.get(i).toString();
                    if (a.getJSONObject(i).getString("objectId").equals(ParseUser.getCurrentUser().getObjectId())) {
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public int getNumComments() {
        return getInt(KEY_NUM_COMMENTS);
    }

    public void increaseNumComments() { put(KEY_NUM_COMMENTS, getNumComments()+1); }


    /*public int getLikedBy() {
        return getJSONArray(KEY_NUM_LIKED_BY);
    }

    public void addLikedBy() { put(KEY_NUM_COMMENTS, getNumComments()+1); }*/

}
