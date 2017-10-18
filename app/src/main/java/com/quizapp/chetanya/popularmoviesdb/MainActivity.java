package com.quizapp.chetanya.popularmoviesdb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    String webUrl = "http://api.themoviedb.org/3/movie/popular?api_key=KEY_HERE";
    URL url;
    String response;
    TextView tvJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvJSON = (TextView)findViewById(R.id.tvJSON);

        try {
            url = new URL(webUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new FetchData().execute(url);
    }

    private class FetchData extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) { //Array of urls
            try {
                return getResponseFromHttpURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("MainActivity", "Response : " + s);
            tvJSON.setText("JSON HERE :->\n" + s);
        }
    }

    private String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        InputStream inputStream = connection.getInputStream();

        Scanner s = new Scanner(inputStream);
        s.useDelimiter("\\A");

        if (s.hasNext())
            return s.next();
        else
            return null;
    }
}
