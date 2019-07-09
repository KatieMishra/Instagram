package com.codepath.instagram.Models;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/* Katie Mishra - FBU 2019 - krmishra@stanford.edu
   ParseApp keeps track of Parse keys and urls for server-client relationship.
 */
public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("katiemishra-instagram")
                .clientKey("katiemishra-instagram")
                .server("http://katiemishra-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
