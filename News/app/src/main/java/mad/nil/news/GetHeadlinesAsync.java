package mad.nil.news;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilan on 2/13/2018.
 */



public class GetHeadlinesAsync extends AsyncTask<String, String, List<String>> {

    NewsFunctions newsFunctions;

    public GetHeadlinesAsync(NewsFunctions newsFunctions) {
        this.newsFunctions = newsFunctions;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        List<String> photoUrlList = new ArrayList<>();
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = null;

        try {
            StringBuilder urlBuilder = new StringBuilder(params[0]);
            urlBuilder.append("?country=" + params[1] + "&category=" + params[2] + "&apiKey=" + params[3]);
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
        return photoUrlList;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        newsFunctions.loadImageURLList(strings);
        newsFunctions.dismissDialog();
    }
}
