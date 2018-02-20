package mad.nil.news;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Inclass Assignment 6
 * File name: NewsFunctions.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */



public interface NewsFunctions {
    void loadHeadlinesDetails(String jsonString);

    void loadHeadlines(List<News> headlines);

    void dismissDialog();

    void showDialog(String message);
}
