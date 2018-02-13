package com.example.test.module5_http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn_isConnectedOnline);
        tv = findViewById(R.id.tv_isConnectedOnline);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tv.setText(String.valueOf(isConnectedOnline()));
                if (isConnectedOnline()){
                    Toast.makeText(MainActivity.this, "Internet connection", Toast.LENGTH_SHORT);
//                    new GetDataAsync().execute("http://api.theappsdr.com/simple.php");
                    RequestParams params = new RequestParams();
                    params.addParameter("name","Bob Smith")
                            .addParameter("age","24")
                            .addParameter("email","bmith@test.com")
                            .addParameter("password","sakjf;laj");
//                    new GetDataParamsUsingGetAsync(params).execute("http://api.theappsdr.com/params.php");
                    new GetDataParamsUsingPostAsync(params).execute("http://api.theappsdr.com/params.php");
                }else {
                    Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_SHORT);
                }
            }
        });

    }

    //method to check if device is connected to internet
    private boolean isConnectedOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected())
//            return true;
        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE))
            return false;
        return true;
    }

//    private class GetDataAsync extends AsyncTask<String, Void, String>{
//
//        @Override
//        protected String doInBackground(String... params) {
//            StringBuilder stringBuilder = new StringBuilder();
//            HttpURLConnection connection = null;
//            InputStream inputStream = null;
//            String result = null;
//
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                    result =  IOUtils.toString(connection.getInputStream(), "UTF8");
//                }
////                inputStream = connection.getInputStream();
////                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////                String line = "";
////                while ((line = reader.readLine()) != null){
////                    stringBuilder.append(line);
////                }
//
////                return IOUtils.toString(connection.getInputStream(), "UTF8");
//
////                return stringBuilder.toString();
//            }catch (MalformedURLException e){
//                e.printStackTrace();
//            }catch (IOException e){
//                e.printStackTrace();
//            } finally {
//                if (connection != null){
//                    connection.disconnect();
//                }
//                if (inputStream != null){
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result){
//            if (result != null){
//                Log.d("demo", result);
//            }else{
//                Log.d("demo","null result");
//            }
//        }
//    }

//    private class GetDataParamsUsingGetAsync extends AsyncTask<String, Void, String>{
//        RequestParams mParams;
//        public GetDataParamsUsingGetAsync(RequestParams params){
//            mParams = params;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            StringBuilder stringBuilder = new StringBuilder();
//            HttpURLConnection connection = null;
//            InputStream inputStream = null;
//            String result = null;
//
//            try {
//                Log.d("demo", mParams.getEncodedUrl(params[0]));
//                URL url = new URL(mParams.getEncodedUrl(params[0]));
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                    result =  IOUtils.toString(connection.getInputStream(), "UTF8");
//                }
////                inputStream = connection.getInputStream();
////                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////                String line = "";
////                while ((line = reader.readLine()) != null){
////                    stringBuilder.append(line);
////                }
//
////                return IOUtils.toString(connection.getInputStream(), "UTF8");
//
////                return stringBuilder.toString();
//            }catch (MalformedURLException e){
//                e.printStackTrace();
//            }catch (IOException e){
//                e.printStackTrace();
//            } finally {
//                if (connection != null){
//                    connection.disconnect();
//                }
//                if (inputStream != null){
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result){
//            if (result != null){
//                Log.d("demo", result);
//            }else{
//                Log.d("demo","null result");
//            }
//        }
//    }

    private class GetDataParamsUsingPostAsync extends AsyncTask<String, Void, String>{
        RequestParams mParams;
        public GetDataParamsUsingPostAsync(RequestParams params){
            mParams = params;
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            String result = null;

            try {
                Log.d("demo", mParams.getEncodedUrl(params[0]));
                URL url = new URL(mParams.getEncodedUrl(params[0]));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                mParams.encodePostParameter(connection);
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    result =  IOUtils.toString(connection.getInputStream(), "UTF8");
                }
//                inputStream = connection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line = "";
//                while ((line = reader.readLine()) != null){
//                    stringBuilder.append(line);
//                }

//                return IOUtils.toString(connection.getInputStream(), "UTF8");

//                return stringBuilder.toString();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            if (result != null){
                Log.d("demo", result);
            }else{
                Log.d("demo","null result");
            }
        }
    }
}
