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
 * Inclass Assignment 6
 * File name: NewsParser.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
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
        ArrayList<News> headlines = parseNewsJSON(jsonString);

        Bundle bundle = new Bundle();
        bundle.putSerializable("headlines", headlines);
        Message message = new Message();
        message.setData(bundle);

        handler.sendMessage(message);
    }

    public ArrayList<News> parseNewsJSON(String jsonString) {
        ArrayList<News> headlines = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray articles = object.getJSONArray("articles");
            for(int i=0; i<articles.length(); i++) {
                JSONObject jsonObject = (JSONObject) articles.get(i);
                News news = parseArticle(jsonObject);
                headlines.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headlines;
    }

    private News parseArticle(JSONObject jsonObject) throws  JSONException {
        News news = new News();
        String title = jsonObject.getString("title");
        if(title == null || title.equals("null")) {
            title = "";
        }
        String description = jsonObject.getString("description");
        if(description == null || description.equals("null")) {
            description = "";
        }
        String urlToImage = jsonObject.getString("urlToImage");
        if(urlToImage == null || urlToImage.equals("null")) {
            urlToImage = "";
        }
        String publishedAt = jsonObject.getString("publishedAt");
        if(publishedAt == null || publishedAt.equals("null")) {
            publishedAt = "";
        }

        news.setTitle(title);
        news.setDescription(description);
        news.setImageUrl(urlToImage);
        news.setPublishedAt(publishedAt);

        return news;
    }
}
