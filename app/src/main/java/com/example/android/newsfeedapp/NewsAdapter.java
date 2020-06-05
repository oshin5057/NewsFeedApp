package com.example.android.newsfeedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import androidx.annotation.NonNull;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView author = listItemView.findViewById(R.id.author_tv);
        author.setText(currentNews.getAuthor());

        TextView section = listItemView.findViewById(R.id.section_tv);
        section.setText(currentNews.getSection());

        TextView title = listItemView.findViewById(R.id.title_tv);
        title.setText(currentNews.getTitle());

        TextView date = listItemView.findViewById(R.id.date_tv);
        date.setText(getDateFromData(currentNews.getDate()));

        TextView time = listItemView.findViewById(R.id.time_tv);
        time.setText(getTimeFromData(currentNews.getDate()));

        return listItemView;
    }

    private String getDateFromData(String dataTimeObject){
        String formattedDate = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        try{
            Date date = inputFormat.parse(dataTimeObject);
             formattedDate = outputFormat.format(date);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return formattedDate;
    }

    private String getTimeFromData(String dateTimeObject){
        String formattedTime = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
        try{
            Date date = inputFormat.parse(dateTimeObject);
            formattedTime = outputFormat.format(date);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return formattedTime;
    }
}
