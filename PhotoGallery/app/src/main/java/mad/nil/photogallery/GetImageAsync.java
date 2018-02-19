package mad.nil.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetImageAsync extends AsyncTask<String, String, Bitmap> {

    ImageFunctions imageFunctions;

    public GetImageAsync(ImageFunctions imageFunctions) {
        this.imageFunctions = imageFunctions;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        List<String> photoUrlList = new ArrayList<>();
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
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
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageFunctions.displayImage(bitmap);
        imageFunctions.dismissDialog();
    }

}