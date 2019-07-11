package com.codepath.instagram.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.codepath.instagram.Models.Post;
import com.codepath.instagram.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/* Katie Mishra - FBU 2019 - krmishra@stanford.edu
   ComposeFragment allows users to take and upload a picture to Instagram.
   Users can also take and upload a profile picture.
 */
public class ComposeFragment extends Fragment {

    private Button btnTakePic;
    private Button btnPost;
    private ImageView postImg;
    private EditText etCaption;
    private final String TAG = "ComposeFragment";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;
    private Button btnUploadProfilePic;

    private ProgressDialog LoadingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTakePic = view.findViewById(R.id.btnTakePic);
        btnPost = view.findViewById(R.id.btnPost);
        postImg = view.findViewById(R.id.postImg);
        etCaption = view.findViewById(R.id.etCaption);
        btnUploadProfilePic = view.findViewById(R.id.btnUpdateProfilePic);

        LoadingBar= new ProgressDialog(getContext());

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caption = etCaption.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                if (photoFile == null || postImg.getDrawable() == null) {
                    Log.e(TAG, "no photo to submit");
                    Toast.makeText(getContext(),"No photo to display!", Toast.LENGTH_SHORT).show();
                } else {
                    savePost(caption, user, photoFile);
                }
            }
        });

        btnUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();
                if (photoFile == null || postImg.getDrawable() == null) {
                    Log.e(TAG, "no photo to submit");
                    Toast.makeText(getContext(),"Take a profile picture to upload!", Toast.LENGTH_SHORT).show();
                } else {
                    uploadProfilePic(user, photoFile);
                }
            }
        });

        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Create a File reference to access to future access
                photoFile = getPhotoFileUri(photoFileName);

                // wrap File object into a content provider
                // required for API >= 24
                // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                // So as long as the result is not null, it's safe to use the intent.
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    // Start the image capture intent to take photo
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the code is the same as the one we started the activity with
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                postImg.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    private void showLoadingBar() {
        LoadingBar.setTitle("Posting to Instagram");
        LoadingBar.setMessage("Give us a moment! Your post will be live soon.");
        LoadingBar.setCanceledOnTouchOutside(true);
        LoadingBar.show();
    }

    private void savePost(String caption, ParseUser user, File photoFile) {
        showLoadingBar();
        Log.d(TAG, "in post");
        Post post = new Post();
        post.setCaption(caption);
        post.setUser(user);
        post.setNumLikes(0);
        post.setImage(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Error while saving");
                    e.printStackTrace();
                    return;
                }
                Log.d(TAG, "Success!!");
                etCaption.setText("");
                postImg.setImageResource(0);
                LoadingBar.hide();
            }
        });
    }

    private void uploadProfilePic(ParseUser user, File photoFile) {
        showLoadingBar();
        Log.d(TAG, "in profile pic");
        user.put("profileImage", photoFile);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                LoadingBar.hide();
            }
        });
    }
}
