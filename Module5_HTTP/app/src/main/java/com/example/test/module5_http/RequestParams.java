package com.example.test.module5_http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by shant on 2/12/2018.
 */
//http://api.theappsdr.com/
public class RequestParams {
    private HashMap<String, String > params;
    private StringBuilder stringBuilder;

    public RequestParams(){
        params = new HashMap<>();
        stringBuilder = new StringBuilder();
    }

    public RequestParams addParameter(String key, String value){
        try {
            params.put(key, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getEncodedParameters(){
        for (String key : params.keySet()) {
            if (stringBuilder.length() >0 ){
                stringBuilder.append("&");
            }
            stringBuilder.append(key + "=" + params.get(key));
        }
        return stringBuilder.toString();
    }

    public String getEncodedUrl(String url){
        return url + "?" + getEncodedParameters();
    }

    public void encodePostParameter(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(getEncodedParameters());
        writer.flush();
    }
}
