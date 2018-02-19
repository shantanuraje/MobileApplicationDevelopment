package com.example.test.triviaquiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTriviaQuestionsAsync.QuestionsData {

    LinearLayout ll_getQuestionsProgress;
    ProgressBar pb_getQuestionsProgress;
    TextView tv_getQuestionsProgress;
    ImageView im_display;

    public static final String QUESTIONS_URL = "http://dev.theappsdr.com/apis/trivia_json/trivia_text.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_getQuestionsProgress = findViewById(R.id.ll_getQuestionsProgress);

        pb_getQuestionsProgress = new ProgressBar(this);
        pb_getQuestionsProgress.setId(View.generateViewId());
        RelativeLayout.LayoutParams pb_LayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pb_LayoutParams.addRule(RelativeLayout.BELOW, R.id.tv_welcomeText);
        pb_getQuestionsProgress.setLayoutParams(pb_LayoutParams);

        tv_getQuestionsProgress = new TextView(this);
        tv_getQuestionsProgress.setId(View.generateViewId());
        tv_getQuestionsProgress.setText(R.string.loadingText);
        RelativeLayout.LayoutParams tv_LayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        tv_LayoutParams.addRule(RelativeLayout.BELOW, pb_getQuestionsProgress.getId());
        tv_LayoutParams.addRule(Gravity.CENTER_HORIZONTAL);
        tv_getQuestionsProgress.setLayoutParams(tv_LayoutParams);

        im_display = new ImageView(this);
        im_display.setImageResource(R.drawable.trivia);
        ll_getQuestionsProgress.addView(pb_getQuestionsProgress);
        ll_getQuestionsProgress.addView(tv_getQuestionsProgress);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isConnected()){
            Log.d("demo", "Is connected");
            new GetTriviaQuestionsAsync(MainActivity.this).execute(QUESTIONS_URL);
            ll_getQuestionsProgress.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this, "Not connected", Toast.LENGTH_LONG);
        }
    }


    @Override
    public void postResult(ArrayList<String> questions) {
        for (String question: questions){
            Log.d("Main: ", question);
        }
        ll_getQuestionsProgress.removeAllViews();
        ll_getQuestionsProgress.addView(im_display);
        tv_getQuestionsProgress.setText(R.string.triviaReady);


    }

    //method to check if device is connected to internet
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE))
            return false;
        return true;
    }
}
