package com.codepath.instagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/* Katie Mishra - FBU 2019 - krmishra@stanford.edu
   Post defines the elements of a post and getters and setters for each item.
 */
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_CAPTION = "caption";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_NUM_LIKES = "likes";
    public static final String KEY_NUM_COMMENTS = "numComments";
    //public static final JSONArray KEY_NUM_LIKED_BY = "likedBy";

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
        return getInt(KEY_NUM_LIKES);
    }

    public void setNumLikes(Integer num) { put(KEY_NUM_LIKES, num); }

    public int getNumComments() {
        return getInt(KEY_NUM_COMMENTS);
    }

    public void increaseNumComments() { put(KEY_NUM_COMMENTS, getNumComments()+1); }

    /*public int getLikedBy() {
        return getJSONArray(KEY_NUM_LIKED_BY);
    }

    public void addLikedBy() { put(KEY_NUM_COMMENTS, getNumComments()+1); }*/

}
