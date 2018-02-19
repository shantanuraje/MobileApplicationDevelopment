package mad.nil.photogallery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Gallery extends AppCompatActivity implements ImageFunctions {

    public static final String NAMES_URL = "http://dev.theappsdr.com/apis/photos/keywords.php";
    public static final String IMAGES_URL = "http://dev.theappsdr.com/apis/photos/index.php";
    public static final String RANDOM = "random";
    private List<String> imageUrlList;
    private ArrayList<String> keywordsList;
    private String currentKeyword;
    private int currentIndex;
    Dialog dialog;
    TextView dialogTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout = new LinearLayout(getBaseContext());
        linearLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(params);
        dialogTextView = new TextView(this);
        dialogTextView.setText(R.string.loading_keywords_message);
        ProgressBar progressBar = new ProgressBar(this);
        linearLayout.addView(dialogTextView);
        linearLayout.addView(progressBar);
        dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(linearLayout);
        dialog.create();
        disableButtons();
    }

    @Override
    protected void onStart() {
        super.onStart();


        currentIndex = -1;
        currentKeyword = "";

        if(isConnected()) {
            getKeywords();
        } else {
            Toast.makeText(Gallery.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            dismissDialog();
        }

        Button go = findViewById(R.id.go_button);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeywords();
            }
        });

        ImageButton next = findViewById(R.id.next_button);
        ImageButton previous = findViewById(R.id.previous_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextImage();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousImage();
            }
        });
    }

    private void getKeywords() {
        new GetKeywordsAsync(this).execute(NAMES_URL);
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showKeywords() {
        if((keywordsList == null || keywordsList.size() == 0) && isConnected()) {
            getKeywords();
        }
        if (keywordsList != null && keywordsList.size() > 0) {
            new AlertDialog.Builder(Gallery.this).setTitle(R.string.dictionary_dialog_title)
                    .setItems(keywordsList.toArray(new String[keywordsList.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String keyword = keywordsList.get(i);
                            if(keyword.equals(RANDOM)) {
                                ArrayList<String> list = new ArrayList<>(keywordsList);
                                list.remove(currentKeyword);
                                keyword = list.get(new Random().nextInt(list.size() - 1));
                            }
                            if(!currentKeyword.equals(keyword)) {
                                if(isConnected()) {
                                    showDialog(getString(R.string.loading_dictionary_message));
                                    new GetImageURLsAsync(Gallery.this).execute(IMAGES_URL, keyword);
                                } else {
                                    Toast.makeText(Gallery.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                                    currentIndex = -1;
                                    loadCurrentImage();
                                    dismissDialog();
                                }
                            } else {
                                if(imageUrlList.size() > 0) {
                                    Toast.makeText(Gallery.this, R.string.already_loaded, Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(Gallery.this, R.string.no_images, Toast.LENGTH_SHORT).show();
                                }
                            }
                            TextView textView = findViewById(R.id.text_keyword);
                            textView.setText(keyword);
                            currentKeyword = keyword;

                        }
                    }).create().show();
        } else {
            Toast.makeText(Gallery.this, R.string.no_keyword_error, Toast.LENGTH_LONG).show();
        }
    }

    public void previousImage() {
        if(imageUrlList != null && imageUrlList.size() > 0 && currentIndex >= 0) {
            if (currentIndex == 0) {
                currentIndex = imageUrlList.size() - 1;
            } else {
                currentIndex--;
            }
            loadCurrentImage();
        } else {
            Toast.makeText(Gallery.this, R.string.no_images, Toast.LENGTH_LONG).show();
        }
    }

    public void disableButtons() {

        ImageButton nextButton = findViewById(R.id.next_button);
        ImageButton previousButton = findViewById(R.id.previous_button);
        nextButton.setClickable(false);
        nextButton.setEnabled(false);
        previousButton.setClickable(false);
        previousButton.setEnabled(false);
    }

    public void enableButtons() {

        ImageButton nextButton = findViewById(R.id.next_button);
        ImageButton previousButton = findViewById(R.id.previous_button);
        nextButton.setClickable(true);
        nextButton.setEnabled(true);
        previousButton.setClickable(true);
        previousButton.setEnabled(true);
    }

    private void loadCurrentImage() {
        showDialog(getString(R.string.loading_photo_message));
        if(currentIndex >= 0) {
            getImageFromURL(imageUrlList.get(currentIndex));
        } else {
            displayImage(null);
        }
    }

    public void nextImage() {
        if(imageUrlList != null && imageUrlList.size() > 0 && currentIndex >= 0) {
            if (currentIndex == imageUrlList.size() - 1) {
                currentIndex = 0;
            } else {
                currentIndex++;
            }
            loadCurrentImage();
        } else {
            Toast.makeText(Gallery.this, R.string.no_images, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loadKeywords(List<String> keywordsList) {
        this.keywordsList = new ArrayList<>(keywordsList);
    }

    @Override
    public void loadImageURLList(List<String> imageURLList) {
        this.imageUrlList = imageURLList;
        if(imageURLList.size() > 0) {
            currentIndex = 0;
            if(imageURLList.size() > 1) {
                enableButtons();
            } else {
                disableButtons();
            }
        } else {
            Toast.makeText(Gallery.this, R.string.no_images, Toast.LENGTH_LONG).show();
            currentIndex = -1;
            disableButtons();
        }
        loadCurrentImage();
    }

    @Override
    public void getImageFromURL(String url) {
        if(isConnected()) {
            new GetImageAsync(this).execute(url);
        } else {
            Toast.makeText(Gallery.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            dismissDialog();
        }
    }

    @Override
    public void displayImage(Bitmap bitmap) {
        ImageView imageView = findViewById(R.id.current_image);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    public void showDialog(String message) {
        if(message == null || message.equals("")) {
            message = getString(R.string.default_loading_message);
        }
        dialogTextView.setText(message);
        dialog.show();
    }
}
