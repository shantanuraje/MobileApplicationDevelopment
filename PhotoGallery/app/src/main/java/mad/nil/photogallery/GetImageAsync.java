/**
 * Inclass 5
 * File name: GetImageAsync.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
package mad.nil.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mad.nil.photogallery.Gallery;
import mad.nil.photogallery.R;

public class GetImageAsync extends AsyncTask<String, Void, Bitmap> {

    ImageData imageData; //interface
//    ImageView imageView;
    Bitmap image = null; //to store downloaded image

    public GetImageAsync(ImageData imageData){
        this.imageData = imageData;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        List<String> photoUrlList = new ArrayList<>();
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = null;
        image = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                image = BitmapFactory.decodeStream(connection.getInputStream());
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
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageData.postResult(bitmap); //send image back to main activity
    }

    // interface to pass downloaded image back to main activity (Gallery)
    public static interface ImageData {
        public void postResult(Bitmap bitmap);
    }
}