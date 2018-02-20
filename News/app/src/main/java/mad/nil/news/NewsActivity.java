package mad.nil.news;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewsActivity extends AppCompatActivity implements NewsFunctions {

    public static final String NEWS_URL = "https://newsapi.org/v2/top-headlines";
    public static final String NEWS_COUNTRY = "us";
    public static final String API_KEY = "5dbfb20add3346c3ad007e38d5427d8e";
    public static final int MAX_POOL_COUNT = 2; //max number of thread pools
    //    public static final String IMAGES_URL = "http://dev.theappsdr.com/apis/photos/index.php";
//    public static final String RANDOM = "random";

    private List<News> headlines;
    private ArrayList<String> categoryList;
    private String currentKeyword;
    private int currentIndex;
    Dialog dialog;
    TextView dialogTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

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

        Resources res = getResources();
//        String[] planets = res.getStringArray(R.array.planets_array);

        if(isConnected()) {
            categoryList = new ArrayList<String>();
            categoryList.add("business");
            categoryList.add("entertainment");
            categoryList.add("general");
            categoryList.add("health");
            categoryList.add("science");
            categoryList.add("sports");
            categoryList.add("technology");
            //{"business", "entertainment", "general", "health", "science", "sports", "technology"};

        }

        Button go = findViewById(R.id.go_button);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeywords();
            }
        });

        ImageView next = findViewById(R.id.next_button);
        ImageView previous = findViewById(R.id.previous_button);

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

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showKeywords() {
        if (categoryList != null && categoryList.size() > 0) {
            new AlertDialog.Builder(NewsActivity.this).setTitle(R.string.dictionary_dialog_title)
                    .setItems(categoryList.toArray(new String[categoryList.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String keyword = categoryList.get(i);
//                            if(keyword.equals(RANDOM)) {
//                                ArrayList<String> list = new ArrayList<>(categoryList);
//                                list.remove(currentKeyword);
//                                keyword = list.get(new Random().nextInt(list.size() - 1));
//                            }
                            if(!currentKeyword.equals(keyword)) {
                                if(isConnected()) {
                                    showDialog(getString(R.string.loading_news_message));
                                    new GetHeadlinesAsync(NewsActivity.this).execute(NEWS_URL,NEWS_COUNTRY, keyword, API_KEY);
                                } else {
                                    Toast.makeText(NewsActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                                    currentIndex = -1;
                                    loadCurrentNews();
                                    dismissDialog();
                                }
                            } else {
                                if(headlines.size() > 0) {
                                    Toast.makeText(NewsActivity.this, R.string.already_loaded, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NewsActivity.this, R.string.no_images, Toast.LENGTH_SHORT).show();
                                }
                            }
                            TextView textView = findViewById(R.id.text_keyword);
                            textView.setText(keyword);
                            currentKeyword = keyword;

                        }
                    }).create().show();
        } else {
            Toast.makeText(NewsActivity.this, R.string.no_keyword_error, Toast.LENGTH_LONG).show();
        }
    }

    public void previousImage() {
        if(headlines != null && headlines.size() > 0 && currentIndex >= 0) {
            if (currentIndex == 0) {
                currentIndex = headlines.size() - 1;
            } else {
                currentIndex--;
            }
            loadCurrentNews();
        } else {
            Toast.makeText(NewsActivity.this, R.string.no_images, Toast.LENGTH_LONG).show();
        }
    }

    public void disableButtons() {

        ImageView nextButton = findViewById(R.id.next_button);
        ImageView previousButton = findViewById(R.id.previous_button);
        nextButton.setClickable(false);
        nextButton.setEnabled(false);
        previousButton.setClickable(false);
        previousButton.setEnabled(false);
    }

    public void enableButtons() {

        ImageView nextButton = findViewById(R.id.next_button);
        ImageView previousButton = findViewById(R.id.previous_button);
        nextButton.setClickable(true);
        nextButton.setEnabled(true);
        previousButton.setClickable(true);
        previousButton.setEnabled(true);
    }

    private void loadCurrentNews() {
        showDialog(getString(R.string.loading_photo_message));
        if(currentIndex >= 0) {
            getImageFromURL(headlines.get(currentIndex).getImageUrl());
        } else {
            displayImage(null);
        }
    }

    public void nextImage() {
        if(headlines != null && headlines.size() > 0 && currentIndex >= 0) {
            if (currentIndex == headlines.size() - 1) {
                currentIndex = 0;
            } else {
                currentIndex++;
            }
            loadCurrentNews();
        } else {
            Toast.makeText(NewsActivity.this, R.string.no_images, Toast.LENGTH_LONG).show();
        }
    }

    public void loadKeywords(List<String> categoryList) {
        this.categoryList = new ArrayList<>(categoryList);
    }

    public void loadHeadlinesDetails(String jsonString) {
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                NewsActivity.this.handleMessage(msg);
                return true;
            }
        });

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(MAX_POOL_COUNT);
        scheduler.schedule(new NewsParser(handler, jsonString), 2, TimeUnit.SECONDS);
    }

    public void loadHeadlines(List<News> headlines) {
        this.headlines = headlines;
        if(headlines.size() > 0) {
            currentIndex = 0;
            if(headlines.size() > 1) {
                enableButtons();
            } else {
                disableButtons();
            }
        } else {
            Toast.makeText(NewsActivity.this, R.string.no_images, Toast.LENGTH_LONG).show();
            currentIndex = -1;
            disableButtons();
        }
        dismissDialog();
        loadCurrentNews();
    }

    public void getImageFromURL(String url) {
        if(isConnected()) {
            new GetImageAsync(this).execute(url);
        } else {
            Toast.makeText(NewsActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            dismissDialog();
        }
    }

    public void displayImage(Bitmap bitmap) {
        ImageView imageView = findViewById(R.id.current_image);
        imageView.setImageBitmap(bitmap);
    }

    public void displayCurrentNews() {
        News news = headlines.get(currentIndex);

    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    public void showDialog(String message) {
        if(message == null || message.equals("")) {
            message = getString(R.string.default_loading_message);
        }
        dialogTextView.setText(message);
        dialog.show();
    }

    public void handleMessage(Message message) {
        Bundle bundle = message.getData();
        headlines = (List<News>) bundle.getSerializable("headlines");
        loadHeadlines(headlines);
    }
}
