package com.example.android.newsfeedapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    public static List<News> fetchNewsData(String requestUrl){
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Error closing input stream", e);
        }

        List<News> news = extractNews(jsonResponse);
        return news;

    }

    private static  URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG,"Error response code: "+ urlConnection.getResponseCode());
            }
        }
        catch (IOException e){
            Log.e(LOG_TAG,"Error response code: " + urlConnection.getResponseCode());
        }
        finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> extractNews(String jsonResponse){

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONObject response = jsonObj.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            int length = results.length();
            for (int i = 0; i<length; i++){
                JSONObject obj = results.getJSONObject(i);

                String section = obj.getString("sectionName");

                String date = "";
                if (obj.has("webPublicationDate")){
                    date = obj.getString("webPublicationDate");
                }

                String title = obj.getString("webTitle");

                String url = obj.getString("webUrl");

                String  authorName = "";
                JSONArray tags = obj.getJSONArray("tags");
                if (tags.length() != 0){
                    JSONObject author = tags.getJSONObject(0);
                    if (author.has("webTitle")){
                        authorName = author.getString("webTitle");
                    }
                }
                News singleNews = new News(authorName, section, title, date, url);
                news.add(singleNews);
            }
        }
        catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the news JSON result", e);
        }
        return news;
    }
}
