package mad.nil.news;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Inclass Assignment 6
 * File name: GetHeadlinesAsync.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */



public class GetHeadlinesAsync extends AsyncTask<String, String, String> {

    NewsFunctions newsFunctions;

    public GetHeadlinesAsync(NewsFunctions newsFunctions) {
        this.newsFunctions = newsFunctions;
    }

    @Override
    protected String doInBackground(String... params) {
        List<String> photoUrlList = new ArrayList<>();
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = null;

        try {
            StringBuilder urlBuilder = new StringBuilder(params[0]);
            urlBuilder.append("?country=" + params[1] + "&category=" + params[2] + "&apiKey=" + params[3]);
            Log.d("demo", urlBuilder.toString());
            URL url = new URL(urlBuilder.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            newsFunctions.dismissDialog();
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
        Log.d("demo", result);
        return result;
    }

    @Override
    protected void onPostExecute(String strings) {
        super.onPostExecute(strings);
        newsFunctions.loadHeadlinesDetails(strings);
    }
}
