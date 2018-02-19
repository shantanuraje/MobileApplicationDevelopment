package com.example.test.triviaquiz;

import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shant on 2/16/2018.
 */

public class GetTriviaQuestionsAsync extends AsyncTask<String, Void, ArrayList<String>> {
    QuestionsData questionsData;

    public GetTriviaQuestionsAsync(QuestionsData questionsData) {
        this.questionsData = questionsData;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
//        enableStrictMode();
        ArrayList<String> questions = null;
//        String[] questions = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String line = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                questions = new ArrayList<>();
//                int count = 0;
                while ((line = reader.readLine()) != null){
                    Log.d("demo", "doInBackground: "+line);
                    questions.add(line);

                }
                //result = reader.lines().toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
        return questions;
    }

    @Override
    protected void onPostExecute(ArrayList<String> questions) {
        questionsData.postResult(questions);
    }

    public static interface QuestionsData{
        public void postResult(ArrayList<String> questions);
    }
}
