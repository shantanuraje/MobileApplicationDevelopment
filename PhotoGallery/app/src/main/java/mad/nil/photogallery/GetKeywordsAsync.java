package mad.nil.photogallery;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nilan on 2/13/2018.
 */


public class GetKeywordsAsync extends AsyncTask<String, String, List<String>> {
    ImageFunctions imageFunctions;

    public GetKeywordsAsync(ImageFunctions imageFunctions) {
        this.imageFunctions = imageFunctions;
    }

    @Override
    protected List<String> doInBackground(String... params) {
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
            imageFunctions.dismissDialog();
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
        return Arrays.asList(photoNames);
    }

    @Override
    protected void onPostExecute(final List<String> strings) {
        super.onPostExecute(strings);
        imageFunctions.loadKeywords(strings);
        imageFunctions.dismissDialog();
    }
}
