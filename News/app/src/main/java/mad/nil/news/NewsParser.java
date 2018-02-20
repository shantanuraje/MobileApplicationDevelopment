package mad.nil.news;

import android.os.Bundle;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

/**
 * Created by nilan on 2/19/2018.
 */

public class NewsParser implements Runnable {

    String jsonString;
    Handler handler;

    public NewsParser(Handler handler, String jsonString) {
        this.handler = handler;
        this.jsonString = jsonString;
    }

    @Override
    public void run() {
        parseNewsJSON(jsonString);
    }

    public void parseNewsJSON(String jsonString) {
        try {
            ArrayList<News> headlines = new ArrayList<>();
            JSONObject object = new JSONObject(jsonString);
            JSONArray articles = object.getJSONArray("articles");
            for(int i=0; i<articles.length(); i++) {
                JSONObject jsonObject = (JSONObject) articles.get(i);
                News news = parseArticle(jsonObject);
                headlines.add(news);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("headlines", headlines);
            Message message = new Message();
            message.setData(bundle);

            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private News parseArticle(JSONObject jsonObject) throws  JSONException {
        News news = new News();
        news.setTitle(jsonObject.getString("title"));
        news.setDescription(jsonObject.getString("description"));
        news.setImageUrl(jsonObject.getString("urlToImage"));
        news.setPublishedAt(jsonObject.getString("publishedAt"));

        return news;
    }
}
