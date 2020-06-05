package com.example.android.newsfeedapp;

import android.content.Context;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null){
            return  null;
        }
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
