package mad.nil.photogallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Gallery extends AppCompatActivity implements GetImageAsync.ImageData {

    public static final String NAMES_URL = "http://dev.theappsdr.com/apis/photos/keywords.php";
    public static final String IMAGES_URL = "http://dev.theappsdr.com/apis/photos/index.php";
    private List<String> imageUrlList;
    private String[] keywordsList;
    AlertDialog.Builder builder;
    private int currentIndex;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        progressBar = new ProgressBar(Gallery.this);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new GetKeywordsAsync().execute(NAMES_URL);

        Button go = findViewById(R.id.go_button);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                } else {
                    Toast.makeText(Gallery.this, "Not connected", Toast.LENGTH_LONG);
                }
            }
        });

        ImageButton next = findViewById(R.id.next_button);
        ImageButton previous = findViewById(R.id.previous_button);

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

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }



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

    private class GetImageURLsAsync extends AsyncTask<String, String, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            progressBar.setVisibility(View.VISIBLE);
            List<String> photoUrlList = new ArrayList<>();
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            String result = null;
            try {
                StringBuilder urlBuilder = new StringBuilder(params[0]);
                urlBuilder.append("?keyword=" + params[1]);
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

            displayImage(imageUrlList.get(currentIndex));
        }
    }

    public void displayImage(String url) {
        //new GetImageAsync().execute(url);
    }
}
