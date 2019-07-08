package com.codepath.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("katiemishra-instagram")
                .clientKey("katiemishra-instagram")
                .server("http://katiemishra-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
