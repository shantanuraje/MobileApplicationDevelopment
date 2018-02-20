package mad.nil.news;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by nilan on 2/13/2018.
 */


public interface NewsFunctions {
    void loadHeadlinesDetails(String jsonString);

    void loadHeadlines(List<News> headlines);

    void dismissDialog();

    void showDialog(String message);
}
