/**
 * Inclass 5
 * File name: Gallery.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
package mad.nil.photogallery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Gallery extends AppCompatActivity implements  GetImageAsync.ImageData{

    public static final String NAMES_URL = "http://dev.theappsdr.com/apis/photos/keywords.php"; //url to get keywords from
    public static final String IMAGES_URL = "http://dev.theappsdr.com/apis/photos/index.php"; //url to get images from
    private List<String> imageUrlList;
    private String[] keywordsList;
    private int currentIndex; //store index of currently display image
    AlertDialog.Builder builder;
    ProgressDialog progressDialog; //show loading dialog while image is being downloaded
    //next and previous buttons
    ImageButton next;
    ImageButton previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
//        progressBar = new ProgressBar(this);
//        progressBar.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Photo ...");

    }

    @Override
    protected void onStart() {
        super.onStart();
        //get list of possible keywords
        new GetKeywordsAsync().execute(NAMES_URL);

        Button go = findViewById(R.id.go_button);
        next = findViewById(R.id.next_button);
        previous = findViewById(R.id.previous_button);
        //set next and previous button as disabled
        next.setEnabled(false);
        previous.setEnabled(false);
        //show alert dialog of list of keywords
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if connected
                if (isConnected()) {
                    new AlertDialog.Builder(Gallery.this).setTitle("Choose a photo")
                            .setItems(keywordsList, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String keyword = keywordsList[i];
                                    if(keyword.equals("random")) {
                                        keyword = keywordsList[new Random().nextInt(keywordsList.length - 1)];
                                        TextView textView = findViewById(R.id.text_keyword);
                                        textView.setText(keyword);
                                    }
                                    new GetImageURLsAsync().execute(IMAGES_URL, keyword);
                                }
                            }).create().show();
                } else if (!isConnected()){
                    //if no internet connection display "no connection toast"
                    Toast.makeText(Gallery.this, "Not connected", Toast.LENGTH_LONG);
                }
            }
        });

        //logic for displaying next image
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUrlList != null && imageUrlList.size() > 0) {
                    if (currentIndex == imageUrlList.size() - 1) {
                        currentIndex = 0;
                    } else {
                        currentIndex++;
                    }
                    displayImage(imageUrlList.get(currentIndex));
                }
            }
        });

        //logic for displaying previous image
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUrlList != null && imageUrlList.size() > 0) {
                    if (currentIndex == 0) {
                        currentIndex = imageUrlList.size() - 1;
                    } else {
                        currentIndex--;
                    }
                    displayImage(imageUrlList.get(currentIndex));
                }
            }
        });
    }

    //method to check if device is connected to internet
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE))
            return false;
        return true;
    }

    //override method that receives image from async task and sets imageView to display that image
    @Override
    public void postResult(Bitmap bitmap) {
        ImageView imageView = findViewById(R.id.current_image);
        imageView.setImageBitmap(bitmap);
        progressDialog.dismiss(); ////hide loading indicator
    }

    //async method to get keywords
    private class GetKeywordsAsync extends AsyncTask<String, String, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String[] photoNames = null;
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            String result = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    result = reader.readLine();
                    photoNames = result.split(";");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(connection !=null) {
                    connection.disconnect();
                }
                if(inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return photoNames;
        }

        @Override
        protected void onPostExecute(final String[] strings) {
            super.onPostExecute(strings);
            keywordsList = strings;

        }
    }

    //async method to get urls of images associated with selected keyword
    private class GetImageURLsAsync extends AsyncTask<String, String, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
//            progressBar.setVisibility(View.VISIBLE);
            List<String> photoUrlList = new ArrayList<>();
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            String result = null;
            try {
                StringBuilder urlBuilder = new StringBuilder(params[0]);
                urlBuilder.append("?keyword=" + params[1]); //append keyword to url
                URL url = new URL(urlBuilder.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    while(true) {
                        String photoUrl = reader.readLine();
                        if(photoUrl == null || photoUrl.length() == 0) {
                            break;
                        }
                        photoUrlList.add(photoUrl);
                    };
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(connection !=null) {
                    connection.disconnect();
                }
                if(inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return photoUrlList;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            imageUrlList = strings;
            currentIndex = 0;
//            progressDialog.show();
            displayImage(imageUrlList.get(currentIndex));

            // set visibility of buttons
            if (imageUrlList.size() == 0 ){
                Toast.makeText(Gallery.this, "No images found", Toast.LENGTH_LONG);
            }else {
                next.setEnabled(true);
                previous.setEnabled(true);
            }
        }
    }

    public void displayImage(String url) {
        new GetImageAsync(Gallery.this).execute(url); //get image using async
        progressDialog.show(); //show loading indicator
    }
}
